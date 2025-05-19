#!/usr/bin/env bash
# Exit on errors, treat unset vars as errors, and pipefail
set -euo pipefail

# Application properties for MySQL
db_host=localhost
db_port=3306
db_name=diabetes_database
db_user=diabetes
db_pass=password

backend_port=8080
frontend_port=5173

# Cleanup function
cleanup() {
  echo "Stopping services..."
  kill "${backend_pid:-}" "${frontend_pid:-}" 2>/dev/null || true
}
trap cleanup EXIT INT TERM

# Ensure MySQL service is running
ensure_mysql() {
  echo -n "Checking MySQL service status... "
  if ! systemctl is-active --quiet mysqld; then
    echo "stopped"
    echo "Attempting to start MySQL service..."
    sudo systemctl start mysqld
    echo -n "Waiting for MySQL to be active... "
    until systemctl is-active --quiet mysql; do
      sleep 1
    done
  fi
  echo "ok"
}

# Create database and user if not exists
setup_database() {
  echo -n "Setting up database '${db_name}' and user '${db_user}'... "
  sudo mysql --protocol=socket <<SQL
CREATE DATABASE IF NOT EXISTS \`${db_name}\`;
CREATE USER IF NOT EXISTS '${db_user}'@'localhost' IDENTIFIED BY '${db_pass}';
GRANT ALL PRIVILEGES ON \`${db_name}\`.* TO '${db_user}'@'localhost';
FLUSH PRIVILEGES;
SQL
  echo "done"
}

# Check connectivity with provided credentials
check_mysql() {
  echo -n "Verifying MySQL connectivity... "
  if ! mysql --host="${db_host}" --port="${db_port}" \
            --user="${db_user}" --password="${db_pass}" \
            --database="${db_name}" --execute="SELECT 1;" &>/dev/null; then
    echo "failed"
    echo "Error: Cannot connect to MySQL as '${db_user}'." >&2
    exit 1
  fi
  echo "ok"
}

# Wait for a port and monitor process
wait_for_port() {
  local port="$1"; shift
  local name="$1"; shift
  local pid="$1"
  echo -n "Waiting for ${name} on port ${port}..."
  while ! nc -z localhost "${port}"; do
    if ! kill -0 "${pid}" &>/dev/null; then
      wait "${pid}"; code=$?
      echo; echo "${name^} exited with code ${code}." >&2
      exit ${code}
    fi
    sleep 1;
  done
  echo "ok"
}

# Main flow
ensure_mysql
setup_database
check_mysql

echo "Starting frontend..."
(
  cd frontend
  export FORCE_COLOR=1
  npm run dev
) &
frontend_pid=$!
wait_for_port "${frontend_port}" frontend "${frontend_pid}"

echo "Starting backend..."
(
  cd backend
  export SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
  ./mvnw clean spring-boot:run
) &
backend_pid=$!
wait_for_port "${backend_port}" backend "${backend_pid}"

echo "Frontend running at http://localhost:${frontend_port}"
echo "Backend running at http://localhost:${backend_port}"

echo;
echo "Services up. Press Ctrl+C to stop."

if ! wait -n "${backend_pid}" "${frontend_pid}"; then
  echo "One service exited unexpectedly." >&2
  exit 1
fi

exit 0
