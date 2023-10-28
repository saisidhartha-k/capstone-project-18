# API Documentation

Welcome to the API documentation for our service, based on the OpenAPI 3.0.1 specification.

## Table of Contents
1. [Introduction](#introduction)
2. [Base URL](#base-url)
3. [Authentication](#authentication)
4. [Software Endpoints](#software-endpoints)
5. [Device Endpoints](#device-endpoints)
6. [Authentication Endpoints](#authentication-endpoints)
7. [RMA Endpoints](#rma-endpoints)
8. [Software Company Endpoints](#software-company-endpoints)
9. [Device Company Endpoints](#device-company-endpoints)
10. [Decommissioned Items Endpoints](#decommissioned-items-endpoints)
11. [Notification Endpoints](#notification-endpoints)
12. [Examples and Usage](#examples-and-usage)
13. [Schemas](#schemas)
14. [Security Schemes](#security-schemes)

## Introduction <a name="introduction"></a>

This documentation provides detailed information about our API, which allows you to interact with our service. It is based on the OpenAPI 3.0.1 specification and is intended to help you understand how to use the available endpoints.

## Base URL <a name="base-url"></a>

- Base URL: [http://localhost:8080](http://localhost:8080)
- Description: This is the base URL for accessing the API endpoints.

## Authentication <a name="authentication"></a>

To access our API, you must use Bearer Token Authentication. Include a valid JWT (JSON Web Token) in the `Authorization` header of your requests.

## Software Endpoints <a name="software-endpoints"></a>

### Renew Software

- **Method**: POST
- **URL**: `/software/renew/{id}`
- **Description**: Renew software with a specified ID.
- **Request**: JSON object with the following fields:

    ```json
    {
        "cost": 100,
        "expiryDate": "2023-12-31",
        "company": {
            "id": 1,
            "name": "Company XYZ",
            "description": "A software company."
        }
    }
    ```

- **Response**: Success message as a string.
- **Security**: Bearer Authentication

### Asset Check

- **Method**: POST
- **URL**: `/software/assetcheck`
- **Description**: Perform an asset check for software.
- **Response**: A list of asset check results in the form of strings.


---

