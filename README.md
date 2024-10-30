# Jobtify-Jobs-Microservice
The jobs microservices is built by SpringBoot with Maven and hosted on AWS. 

## Links and URLs
### Postman
https://www.postman.com/software-engineering-project/coms4153-jobtify/overview



## Local Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/jobs-microservice.git
   ```
2. Navigate to the project directory:
    ```bash
   cd jobs-microservice
   ```
3. Build the project using Maven:
    ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## AWS Hosting / Local Hosting via DockerHub
1. Fetch the docker image from
    ```bash
   docker pull louislu000/jobs-microservice:latest
   ```
2. Run the docker image
     ```bash
   docker run -d --platform linux/arm64  -p 8080:8080   -e DB_URL="DB_URL" -e DB_USERNAME="DB_USERNAME" -e DB_PASSWORD="DB_PASSWORD" louislu000/jobs-microservice:latest
   ``` 

## Database Connection
- This project uses MySQL Database powered by AWS RDS.
- To run the application locally, an .env file containing the database connection credentials needs to be created at the root directory of the project.

## Endpoints
This project uses `springdoc-openapi` for generating and displaying API documentation. By default, you can access the OpenAPI JSON and Swagger UI at the following paths:

- **OpenAPI JSON**: `/api-docs`
- **Swagger UI**: `/swagger-ui`

### Get All Jobs

- **URL**: `/api/jobs`
- **Method**: `GET`
- **Description**: Retrieve a paginated list of all available job postings.
- **Query Parameters**:
    - `page` (Integer): Page number, starting from 0. Default is 0.
    - `size` (Integer): Number of records per page. Default is 10.
- **Response**: Returns a `Page` of job objects.
- **Response Codes**:
    - `200 OK` – Successfully retrieved list of jobs.
    - `400 Bad Request` – Invalid pagination parameters.
    - `500 Internal Server Error` – Unexpected server error.

### Get Job by ID

- **URL**: `/api/jobs/{id}`
- **Method**: `GET`
- **Description**: Retrieve details of a specific job using its ID.
- **Path Parameter**:
    - `id` (Long): The ID of the job to retrieve.
- **Response**: Returns the job object if found.
- **Response Codes**:
    - `200 OK` – Job found and returned.
    - `404 Not Found` – No job found with the specified ID.
    - `500 Internal Server Error` – Unexpected server error.

### Create a New Job

- **URL**: `/api/jobs`
- **Method**: `POST`
- **Description**: Create a new job listing.
- **Request Body**: A JSON object representing the new job (see [Models](#models)).
- **Response**: Returns the created job object.
- **Response Codes**:
    - `201 Created` – Job successfully created.
    - `400 Bad Request` – Invalid job data.
    - `500 Internal Server Error` – Unexpected server error.

### Update a Job

- **URL**: `/api/jobs/{id}`
- **Method**: `PUT`
- **Description**: Update an existing job listing by ID.
- **Path Parameter**:
    - `id` (Long): The ID of the job to update.
- **Request Body**: A JSON object with the updated job details.
- **Response**: Returns the updated job object if successful.
- **Response Codes**:
    - `200 OK` – Job successfully updated.
    - `404 Not Found` – No job found with the specified ID.
    - `400 Bad Request` – Invalid job data.
    - `500 Internal Server Error` – Unexpected server error.

### Delete a Job

- **URL**: `/api/jobs/{id}`
- **Method**: `DELETE`
- **Description**: Delete an existing job by its ID.
- **Path Parameter**:
    - `id` (Long): The ID of the job to delete.
- **Response**: No content.
- **Response Codes**:
    - `204 No Content` – Job successfully deleted.
    - `404 Not Found` – No job found with the specified ID.
    - `500 Internal Server Error` – Unexpected server error.

### Search Jobs

- **URL**: `/api/jobs/search`
- **Method**: `GET`
- **Description**: Search for jobs by title or location.
- **Query Parameters**:
    - `title` (String): The job title to search for.
    - `location` (String): The job location to search for.
- **Response**: Returns a list of matching job objects.
- **Response Codes**:
    - `200 OK` – Successfully retrieved list of matching jobs.
    - `400 Bad Request` – Invalid search parameters.
    - `500 Internal Server Error` – Unexpected server error.

### Asynchronously Update Job Applicant Count

- **URL**: `/api/jobs/async/update/{jobId}`
- **Method**: `POST`
- **Description**: Increment the applicant count for a specified job by 1 asynchronously.
- **Path Parameter**:
    - `jobId` (Long): The ID of the job to update.
- **Response**: URI to check the job's status.
- **Response Codes**:
    - `202 Accepted` – Job applicant count update accepted for asynchronous processing.
    - `404 Not Found` – No job found with the specified ID.
    - `500 Internal Server Error` – Unexpected server error.

### Get Job Status by ID

- **URL**: `/api/jobs/status/{id}`
- **Method**: `GET`
- **Description**: Retrieve the current status of a job by its ID.
- **Path Parameter**:
    - `id` (Long): The ID of the job to check the status of.
- **Response**: The current status of the job.
- **Response Codes**:
    - `200 OK` – Job status retrieved successfully.
    - `404 Not Found` – No job found with the specified ID.
    - `500 Internal Server Error` – Unexpected server error.

### Handle Job Completion Callback

- **URL**: `/api/jobs/default-job-completion-callback`
- **Method**: `POST`
- **Description**: This endpoint is used as a callback URL to receive job completion details.
- **Request Body**: A JSON object representing job details.
- **Response**: Returns the job details and a link to access the job.
- **Response Codes**:
    - `201 Created` – Job processed successfully.
    - `400 Bad Request` – Invalid job data.
    - `500 Internal Server Error` – Unexpected server error.


## Data Models
### Job Model
The `Job` object has the following fields:

- `jobId` (Long): Unique identifier for the job.
- `publicView` (Boolean): Whether the job is publicly visible.
- `company` (String): The name of the company offering the job.
- `title` (String): The title of the job.
- `description` (String): A detailed description of the job.
- `salary` (Double): The salary offered for the job.
- `location` (String): The location of the job.
- `industry` (String): The industry category for the job.
