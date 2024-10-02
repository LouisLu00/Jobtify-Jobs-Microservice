# Jobtify-Jobs-Microservice
Repository for Jobs Microservice of Jobtify

## Database Connection
- This project uses MySQL Database powered by AWS RDS.
- To run the application locally, an .env file containing the database connection credentials needs to be created at the root directory of the project.

## Endpoints
### Get All Jobs

- **URL**: `/api/jobs`
- **Method**: `GET`
- **Description**: Retrieve a list of all available job postings.
- **Response**: Returns a list of job objects.
- **Response Codes**:
    - `200 OK` – Success

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

### Create a New Job

- **URL**: `/api/jobs`
- **Method**: `POST`
- **Description**: Create a new job listing.
- **Request Body**: A JSON object representing the new job (see [Models](#models)).
- **Response**: Returns the created job object.
- **Response Codes**:
    - `200 OK` – Job successfully created.

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

### Delete a Job

- **URL**: `/api/jobs/{id}`
- **Method**: `DELETE`
- **Description**: Delete an existing job by its ID.
- **Path Parameter**:
    - `id` (Long): The ID of the job to delete.
- **Response**: No content.
- **Response Codes**:
    - `204 No Content` – Job successfully deleted.

### Search Jobs

- **URL**: `/api/jobs/search`
- **Method**: `GET`
- **Description**: Search for jobs by title or location.
- **Query Parameters**:
    - `title` (String): The job title to search for.
    - `location` (String): The job location to search for.
- **Response**: Returns a list of matching job objects.
- **Response Codes**:
    - `200 OK` – Success.

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