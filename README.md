# Diabetes Logger
This is a university project for an application that allows diabetes patients to
log their blood sugar levels, insulin doses, and other relevant information;
medics can then access this data to monitor their patients' health and admins
can manage users and their data.

## Project Structure
The project is structured as follows:

### Backend
Written in Java using Spring Boot, the backend handles the business logic and
data storage. It profides RESTful APIs for the frontend to interact with.

### Frontend
Written in SvelteKit, it interacts with the backend APIs to provide a simple
user interface.

### Database
The project uses MySQL for data storage. The database schema is defined in the
backend by JPA entities.

## Requirements
### Backend
- Java 21 or higher

### Frontend
- Node.js 22 or higher
- npm or yarn

### Database
- MySQL 8 or higher

## Build and Run
To build and run the project, there is a script in the root directory for linux:
```bash
./start.sh
```
and one for macOS:
```bash
./startmac.sh
```
Before running it, you need to install the following tools via brew or other package manager for MacOs:
```bash
brew install msql
brew install maven
brew install node
```

Before running the project, you need to install the frontend dependencies:
```bash
cd frontend
npm install
```

When the project is running, you can access the frontend at port `5173` and the backend at port `8080`.

## Features
- User authentication and authorization with roles (admin, medic, patient) using JWT tokens.
- Patients can log their blood sugar levels, insulin doses, and other relevant information.
- Medics can view their patients' data and monitor their health.
- Admins can manage users and their data.
- Data validation and error handling.
- Secure API endpoints with role-based access control.

## Documentation
You can find all the project [requirements](./documentation/requirements.pdf) and [documentation](./documentation/documentation.pdf) in the `documentation` directory.
