## Overview

This React.js project is organized into several folders, each serving a specific purpose. Below, you will find documentation for each of these folders.

- [`components`](#components)
- [`hooks`](#hooks)
- [`context`](#context)
- [`page`](#page)
- [`test`](#test)

## `components`

### Purpose
The `components` folder contains reusable React components used throughout the project. These components can be composed to build various parts of the application's user interface.

#### `add`

##### Purpose
The `add` subfolder contains components related to adding software and devices.

##### Structure
- `addsoftware.js`: Description of the "Add Software" component.
- `adddevice.jsx`: 

### Purpose
The `adddevice.jsx` file is a component responsible for adding new devices to the application. It is a part of the `add` subfolder within the `components` directory.

### Usage
- The `adddevice.jsx` component is typically utilized within the application's user interface to collect and submit data for adding new devices.

### Structure
- The component facilitates the following key functionalities:
   - Collecting device information, including device name, company name, number of employees, cost, expiry date, and location.
   - Allowing users to select an existing company from a dropdown or manually input a new company name and description.
   - Handling the submission of device information to the server and providing feedback through toast notifications.
   - Offering the flexibility to switch between "Add" and "Renew" modes.

### Example
```jsx
import React from 'react';
import DeviceForm from './DeviceForm';

function AddDevicePage() {
  return (
    <div>
      <h1>Add Device</h1>
      <DeviceForm />
    </div>
  );
}

export default AddDevicePage;

##### Structure
- `addsoftware.jsx`: 

### Purpose
The `addsoftware.jsx` file is a component responsible for adding new software to the application. It is a part of the `add` subfolder within the `components` directory.

### Usage
- The `addsoftware.jsx` component is typically utilized within the application's user interface to collect and submit data for adding new software.

### Structure
- `addsoftware.jsx` is structured as follows:

#### 1. Software Information Collection
   - The component provides input fields and controls for collecting the following software information:
     - Software Name
     - Company Name
     - Number of Employees
     - Cost
     - Expiry Date
     - Is Expired (a boolean flag)

#### 2. Company Selection
   - Users can select an existing company from a dropdown menu or manually input a new company name and description if necessary.

#### 3. Software Renewal
   - Users can switch between "Add" and "Renew" modes, allowing them to either add new software or renew existing software licenses.

#### 4. Toast Notifications
   - Toast notifications from the `react-toastify` library are used to provide feedback to the user upon successful or failed software addition or renewal.

### Example
```jsx
import React from 'react';
import SoftwareForm from './SoftwareForm';

function AddSoftwarePage() {
  return (
    <div>
      <h1>Add Software</h1>
      <SoftwareForm />
    </div>
  );
}

export default AddSoftwarePage;