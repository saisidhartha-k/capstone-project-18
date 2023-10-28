# API Documentation

This is the API documentation for Liscence Mangement System, based on the OpenAPI 3.0.1 specification.

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

## Add Software

- **Method**: POST
- **URL**: `/addsoftware`
- **Description**: Add new software to the system.
- **Request**: JSON object representing the software to be added.
  ```json
  {
    "name": "New Software",
    "licenseKey": "ABCD-1234-EFGH-5678",
    "expiryDate": "2024-12-31",
    "cost": 1000,
    "company": {
      "id": 1,
      "name": "Another Company",
      "description": "Another software company."
    }
  }
  ```
- **Response**: The added software as a JSON object.

## Get Not Expired Software

- **Method**: GET
- **URL**: `/getNotExpired`
- **Description**: Get a list of software that has not expired.
- **Response**: A list of software objects that are not expired.

## Get Expired Software

- **Method**: GET
- **URL**: `/getExpired`
- **Description**: Get a list of expired software.
- **Response**: A list of software objects that have expired.

## Get About to Expire Software

- **Method**: GET
- **URL**`: `/getAboutExpired`
- **Description**`: Get a list of software that is about to expire.
- **Response**`: A list of software objects that are about to expire.

## Get All Software

- **Method**: GET
- **URL**`: `/get`
- **Description**`: Get a list of all available software.
- **Response**`: A list of software objects.

## Get Expired Software Count

- **Method**: GET
- **URL**`: `/getExpiredCount`
- **Description**`: Get the count of expired software.
- **Response**`: The count of expired software as an integer.

## Get Not Expired Software Count

- **Method**: GET
- **URL**`: `/getNotExpiredCount`
- **Description**`: Get the count of not expired software.
- **Response**`: The count of not expired software as an integer.

## Get About to Expire Software Count

- **Method**: GET
- **URL**`:`/getAboutExpiredCount`
- **Description**`: Get the count of software that is about to expire.
- **Response**`: The count of software about to expire as an integer.

## Renew Software

- **Method**: POST
- **URL**`: `/renew/{id}`
- **Description**`: Renew software with a specified ID.
- **Request**: JSON object with the renewal details.
  ```json
  {
    "cost": 100,
    "expiryDate": "2023-12-31",
    "company": {
      "id": 1,
      "name": "Company XYZ"
    }
  }
  ```
- **Response**: A success message as a string.

## Get Percentage of Software About to Expire

- **Method**: GET
- **URL**`: `/percentageAboutToExpire`
- **Description**`: Get the percentage of software that is about to expire.
- **Response**: The percentage as an integer.

## Get Percentage of Not Expired Software

- **Method**: GET
- **URL**`: `/percentageNotExpired`
- **Description**`: Get the percentage of software that is not expired.
- **Response**: The percentage as an integer.

## Get Percentage of Expired Software

- **Method**: GET
- **URL**`: `/percentageExpired`
- **Description**`: Get the percentage of expired software.
- **Response**: The percentage as an integer.

## Decommission Software

- **Method**: DELETE
- **URL**`: `/decommission/{id}`
- **Description**`: Decommission software by ID.
- **Response**: No content. The software is decommissioned.

## Asset Check

- **Method**: POST
- **URL**`: `/assetcheck`
- **Description**`: Perform an asset check for software.
- **Response**: A list of asset check results in the form of strings.

## Device Endpoints <a name="device-endpoints"></a>

### Add Device

- **Method**: POST
- **URL**: `/device/adddevice`
- **Description**: Add a new device to the system.
- **Request**: JSON object representing the device to be added.
  ```json
  {
    "name": "New Device",
    "serialNumber": "12345-ABCDE",
    "expiryDate": "2024-12-31",
    "cost": 1000,
    "Location": "Hyderabad",
    "company": {
      "id": 2,
      "name": "Device Company",
      "description": "A device company."
    }
  }
  ```
- **Response**: The added device as a JSON object.

### Renew Device

- **Method**: POST
- **URL**: `/device/renew/{id}`
- **Description**: Renew a device with a specified ID.
- **Request**: JSON object with the renewal details.
  ```json
  {
    "cost": 1000,
    "expiryDate": "2023-12-31",
    "company": {
      "id": 1,
      "name": "Company XYZ"
    }
  }
  ```
- **Response**: A success message as a string.

### Get Expired Devices

- **Method**: GET
- **URL**: `/device/getExpired`
- **Description**: Get a list of expired devices.
- **Response**: A list of device objects that have expired.

### Get Devices About to Expire

- **Method**: GET
- **URL**: `/device/getAboutExpired`
- **Description**: Get a list of devices that are about to expire.
- **Response**: A list of device objects that are about to expire.

### Get Devices About to Expire Count

- **Method**: GET
- **URL**: `/device/aboutToExpireCount`
- **Description**: Get the count of devices that are about to expire.
- **Response**: The count of devices about to expire as an integer.

### Get Not Expired Device Count

- **Method**: GET
- **URL**: `/device/notExpiredCount`
- **Description**: Get the count of devices that are not expired.
- **Response**: The count of devices that are not expired as an integer.

### Get Expired Devices Count

- **Method**: GET
- **URL**: `/device/expiredCount`
- **Description**: Get the count of expired devices.
- **Response**: The count of expired devices as an integer.

### Get Percentage of Devices About to Expire

- **Method**: GET
- **URL**: `/device/percentageAboutToExpire`
- **Description**: Get the percentage of devices that are about to expire.
- **Response**: The percentage as an integer.

### Get Percentage of Not Expired Devices

- **Method**: GET
- **URL**: `/device/percentageNotExpired`
- **Description**: Get the percentage of devices that are not expired.
- **Response**: The percentage as an integer.

### Get Percentage of Expired Devices

- **Method**: GET
- **URL**: `/device/percentageExpired`
- **Description**: Get the percentage of expired devices.
- **Response**: The percentage as an integer.

### Decommission Device

- **Method**: DELETE
- **URL**: `/device/decomissionDevice/{id}`
- **Description**: Decommission a device by ID.
- **Response**: No content. The device is decommissioned.

### Asset Check

- **Method**: POST
- **URL**: `/device/assetcheck`
- **Description**: Perform an asset check for devices.
- **Response**: A list of asset check results in the form of strings.

## Authentication <a name="authentication"></a>

To access our API, you must use Bearer Token Authentication. Include a valid JWT (JSON Web Token) in the `Authorization` header of your requests.

## Authentication Endpoints <a name="authentication-endpoints"></a>

### Obtain JWT Token

- **Method**: POST
- **URL**: `/api/auth/token`
- **Description**: Obtain a JSON Web Token (JWT) for authentication.
- **Request**: JSON object containing the username and password.
  ```json
  {
    "username": "admin",
    "password": "admin"
  }
  ```
- **Response**: A JWT response with the user's ID, token, and username.
  ```json
  {
    "userId": 1,
    "token": "example-jwt-token",
    "username": "username"
  }
  ```

## RMA Endpoints <a name="rma-endpoints"></a>

### Move Software to RMA

- **Method**: POST
- **URL**: `/RMA/moveSoftware/{softwareId}`
- **Description**: Move software with a specified ID to the Return Merchandise Authorization (RMA) with a provided reason.
- **Request**: JSON object containing the reason for returning the software to RMA.
  ```json
  {
    "reason": "Defective software"
  }
  ```
- **Response**: No content. The software is moved to RMA.

### Move Device to RMA

- **Method**: POST
- **URL**: `/RMA/moveDevice/{deviceId}`
- **Description**: Move a device with a specified ID to the Return Merchandise Authorization (RMA) with a provided reason.
- **Request**: JSON object containing the reason for returning the device to RMA.
  ```json
  {
    "reason": "Faulty device"
  }
  ```
- **Response**: No content. The device is moved to RMA.

### Put Back Software from RMA

- **Method**: POST
- **URL**: `/RMA/putBackSoftware/{id}`
- **Description**: Return software from RMA with a specified ID to normal operation.
- **Response**: No content. The software is returned from RMA.

### Put Back Device from RMA

- **Method**: POST
- **URL**: `/RMA/putBackDevice/{id}`
- **Description**: Return a device from RMA with a specified ID to normal operation.
- **Response**: No content. The device is returned from RMA.

### Get RMA Items

- **Method**: GET
- **URL**: `/RMA/getRma`
- **Description**: Get a list of items currently in the Return Merchandise Authorization (RMA).
- **Response**: A list of RMA objects representing items in RMA.

---
