# Happy QA

## Table of Contents

- [Description](#description)
- [Prerequisites](#prerequisites)
- [How to Build and Run](#how-to-build-and-run)
    - [Running the App with Maven](#running-the-app-with-maven)
    - [Running the App with Docker](#running-the-app-with-docker)
- [Input](#input)
- [Output](#output)

---

## Description

**Happy QA** is a Java-based application that solves the problem of scheduling release testing for a given sprint based on
release delivery schedule and estimated testing time.

**Basic algorithm** helps testers find the optimal schedule and maximize the number of tested releases when testing of a
release must always start on the same day as delivery.

**Advanced algorithm** considers the possibility of postponing the start testing day of a release.

---

## Input

File **releases.txt** with multiple lines. Each line contains two integers separated with a
space. First integer – day of a sprint (1-10) when a release is delivered for tester’s validation. Second integer –
number of days required to validate a release. **releases.txt** file is located in the root of the repository.

1. **Example `releases.txt` content**:

    ```bash
    1 1
    2 1
    3 1
    9 1
    10 4
    10 2
    9 5
    10 3
    4 5 
    ```
---

## Output

The application generates two files **output.txt** and **output-advanced.txt**.

1. **`output.txt`** format:
    - **First Line**: The maximum number of releases that can be tested in a sprint for the given input.
    - **Following Lines**: A list of those releases. Each release is represented by the **delivery day** and **last day
      of the release testing**.

   **Example `output.txt` content**:
    ```bash
    5 
    1 1
    2 2
    3 3
    4 8
    9 9
    ```
2. **`output-advanced.txt`** format:
    - **First Line**: The maximum number of releases that can be tested in a sprint for the given input.
    - **Following Lines**: A list of those releases. Each release is represented by the following details:
        - **release id**
        - **start testing day**
        - **end testing day**
        - **delivery day**
        - **time to test**

   **Example `output-advanced.txt` content**:
   ```bash
   5
   Release A {startTestingDay=1, endTestingDay=1, deliveryDay=1, timeToTest=1}
   Release B {startTestingDay=2, endTestingDay=2, deliveryDay=2, timeToTest=1}
   Release C {startTestingDay=3, endTestingDay=3, deliveryDay=3, timeToTest=1}
   Release I {startTestingDay=4, endTestingDay=8, deliveryDay=4, timeToTest=5}
   Release D {startTestingDay=9, endTestingDay=9, deliveryDay=9, timeToTest=1}
   ```

## Prerequisites

Before running the application, ensure you have the following installed on your machine:

- Java 17 or higher
- Maven 3.8.4 or higher
- Docker (for containerized execution)

---

## How to Build and Run

You can run the application either using **Maven** or **Docker**. Both methods are described below:

### Running the App with Maven

1. **Clone the repository** and navigate into the project folder:
    ```bash
    git clone https://github.com/akuiz/happyQA.git
    cd happyQA
    ```

2. Run the application using Maven with one of the available profiles (`basicAlgorithm` or `advancedAlgorithm`). You
   can pass system properties like the input file name, sprint duration, and output file name. If no parameters passed,
   default ones are used from the description.

#### Example Commands:

- **Basic Algorithm**:
    ```bash
    mvn clean compile exec:java -PbasicAlgorithm
    ```

- **Advanced Algorithm**:
    ```bash
    mvn clean compile exec:java -PadvancedAlgorithm
    ```
- **Configuration**: The application can be also customized using the following system properties:

    - **sprint.duration**: Defines the sprint duration in the application.
    - **releases.file.name**: Defines input file name with releases.
    - **output.file.name**: Defines output file name with optimal release schedule based on selected algorithm.
    - **output.file.name.advanced**: Defines output file name with advanced details for optimal release schedule based on selected algorithm.
  
    ```bash
    -Dsprint.duration=10
    -Dreleases.file.name=releases.txt 
    -Doutput.file.name=output.txt
    -Doutput.file.name.advanced=output-advanced.txt
  
- **Example**:
    ```bash
    mvn clean compile exec:java -PadvancedAlgorithm -Dsprint.duration=10 -Dreleases.file.name=releases.txt -Doutput.file.name=output.txt -Doutput.file.name.advance=output-advanced.txt
    ```

### Running the App with Docker

1. **Clone the repository** and navigate into the project folder:
    ```bash
    git clone https://github.com/akuiz/happyQA.git
    cd happyQA
    ```

2. **Build the Docker image from docker file**:
    ```bash
    docker build -t happyQA .
    ```

3. Run the application in a Docker container, specifying the algorithm type (`basic` or `advanced`) and optional
   parameters such as the input/output files. Or use predefined docker-compose configurations for basic and advanced algorithms.

#### Example Commands:

- **Basic Algorithm (Default)**:

  - Docker run
  ```bash
  docker run --rm -e ALGORITHM_TYPE=basic -v $(pwd)/:/app/output -v $(pwd)/releases.txt:/app/releases.txt happyQA
  ```
  - Docker compose
  ```bash
  ALGORITHM_TYPE=basic docker-compose up -d
  ```
  
- **Advanced Algorithm**:
  - Docker run
  ```bash
  docker run --rm -e ALGORITHM_TYPE=advanced -v $(pwd)/:/app/output -v $(pwd)/releases.txt:/app/releases.txt happyQA
  ```
  - Docker compose
  ```bash
  ALGORITHM_TYPE=advanced docker-compose up -d
  ```

- **Configuration**: The application can be also customized using the following environment variables:

    - **SPRINT_DURATION**: Defines the sprint duration in days.
    - **ALGORITHM_TYPE**: Algorithm type.
  
  ```bash
    -e SPRINT_DURATION=10
    -e ALGORITHM_TYPE=basic

- **Example**:
    ```bash
   docker run --rm \
    -e ALGORITHM_TYPE=basic \
    -e SPRINT_DURATION=5 \
    -v $(pwd)/:/app/output \
    -v $(pwd)/releases.txt:/app/releases.txt
    happyQA
    ```
  ```bash
  ALGORITHM_TYPE=advanced SPRINT_DURATION=10 docker-compose up -d
    ```
  
