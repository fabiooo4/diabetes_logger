#!/usr/bin/env bash
set -euo pipefail

# Application properties for MySQL
db_host=localhost
db_port=3306
db_name=diabetes_database
db_user=diabetes
db_pass=password

mysql_socket="/opt/homebrew/var/mysql/mysql.sock"

backend_port=8080
frontend_port=5173

cleanup() {
  echo "Stopping services..."
  # Kill backend and frontend if running
  [[ -n "${backend_pid:-}" ]] && kill "${backend_pid}" 2>/dev/null || true
  [[ -n "${frontend_pid:-}" ]] && kill "${frontend_pid}" 2>/dev/null || true

  # Kill any process still listening on backend/frontend ports
  lsof -ti tcp:"${backend_port}" | xargs -r kill -9
  lsof -ti tcp:"${frontend_port}" | xargs -r kill -9
}
trap cleanup EXIT INT TERM

ensure_mysql() {
  echo -n "Checking MySQL service status... "
  if ! brew services list | grep -q '^mysql.*started'; then
    echo "stopped"
    echo -n "Starting MySQL service... "
    brew services start mysql
    sleep 5
  fi
  echo "ok"
}

setup_database() {
  echo -n "Setting up database '${db_name}' and user '${db_user}'... "
  mysql --protocol=tcp -h "${db_host}" -P "${db_port}" -u root -p <<SQL
CREATE DATABASE IF NOT EXISTS \`${db_name}\`;
CREATE USER IF NOT EXISTS '${db_user}'@'localhost' IDENTIFIED BY '${db_pass}';
GRANT ALL PRIVILEGES ON \`${db_name}\`.* TO '${db_user}'@'localhost';
FLUSH PRIVILEGES;
SQL
  echo "done"
}

check_mysql() {
  echo -n "Verifying MySQL connectivity... "
  if ! mysql --protocol=tcp -h "${db_host}" -P "${db_port}" \
    -u "${db_user}" -p"${db_pass}" \
    --database="${db_name}" --execute="SELECT 1;" &>/dev/null; then
    echo "failed"
    echo "Error: Cannot connect to MySQL as '${db_user}'." >&2
    exit 1
  fi
  echo "ok"
}

wait_for_port() {
  local port="$1"
  local name="$2"
  local pid="$3"
  echo -n "Waiting for ${name} on port ${port}..."
  while ! nc -z localhost "${port}"; do
    if ! kill -0 "${pid}" &>/dev/null; then
      wait "${pid}"; code=$?
      echo; echo "${name^} exited with code ${code}." >&2
      exit ${code}
    fi
    sleep 1
  done
  echo "ok"
}

test_backend() {
  echo "Testing backend..."
  cd backend
  export SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
  ./mvnw test
  exit 0
}

start_applications() {
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

  echo
  echo "Services up. Press Ctrl+C to stop."

  # Wait for either process to exit
  while true; do
    wait "${backend_pid}" && break
    wait "${frontend_pid}" && break
  done

  echo "One service exited unexpectedly." >&2
  exit 1
}

# Main flow
ensure_mysql
setup_database
check_mysql

if [[ "${1:-}" == "test-backend" ]]; then
  test_backend
else
  start_applications
fi

exit 0
