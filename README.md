# payment-app
A simple payment application to demonstrate the integrated use of technologies such as Java/Spring Boot/React/Docker

# Project Name

This project is organized into two main services: the backend, written in Spring Boot, and the frontend, written in ReactJS. 

Below you will find the instructions to set up and run the project.

## Prerequisites

To run this application, you will need to have the following dependencies installed:

- Docker
- Docker Compose
- Make

## Setup and Run

1. **Clone the repository**:
```sh
   git clone https://github.com/dpavanelli/payment-app.git
   cd payment-app
```

## Build and Start the Application:

To build all services and start the application, run the following command:

```sh
make build start
```

This command will:

- Build the backend and frontend
- Start the necessary modules (PostgreSQL database, backend, and frontend)

## Access the Application:

Once the application is running, you can access the UI at: http://localhost:3000

The API exposed by the backend is documented and can be accessed at: http://localhost:8080/swagger-ui/index.html.

## Stopping the Application

To stop the running application, use the following command:

```sh
make stop
```