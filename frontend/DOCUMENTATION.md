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
