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

#### add

##### Purpose
The `add` subfolder contains components related to adding software and devices.

##### Structure
- `addsoftware.jsx`
- `adddevice.jsx` 

### Purpose
The `adddevice.jsx` file is a component responsible for adding new devices to the application. It is a part of the `add` subfolder within the `components` directory.

### Usage
- The `adddevice.jsx` component is typically utilized within the application's user interface to collect and submit data for adding new devices.

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
```

### Purpose
The `addsoftware.jsx` file is a component responsible for adding new software to the application. It is a part of the `add` subfolder within the `components` directory.

### Usage
- The `addsoftware.jsx` component is typically utilized within the application's user interface to collect and submit data for adding new software.

- The component facilitates the following key functionalities:
   - Collecting device information, including software name, company name, number of employees, cost, expiry date, and .
   - Allowing users to select an existing company from a dropdown or manually input a new company name and description.
   - Handling the submission of device information to the server and providing feedback through toast notifications.
   - Offering the flexibility to switch between "Add" and "Renew" modes.

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
```
### DashboardChart Component

#### Purpose
The `DashboardChart` subcomponent is a part of the `components` directory and is responsible for visualizing software and device usage data using interactive line charts. Users can switch between viewing software and device data, and the subcomponent fetches the relevant data from the server. The charts are rendered using the `recharts` library.

### Usage
- This subcomponent is designed to be used on the application's dashboard page.
- It provides an intuitive way to visualize software and device usage data.

### Structure
- The `DashboardChart` subcomponent structure is as follows:

#### Data Selection
- Users can toggle between software and device data using a switch.

#### Data Fetching
- Data is fetched from the server based on the selected data type (software or device) using the `getSoftwares` and `getDevices` functions.
- The subcomponent initiates data fetching whenever the data type changes.

#### Chart Display
- A responsive line chart is displayed using the `recharts` library.
- The chart shows usage data, including product name, the number of employees using the product, and the associated company.
- Chart properties, including CartesianGrid, XAxis, YAxis, and a custom Tooltip, are configured.

#### Toggle Switch
- Users can switch between viewing software and device data using a toggle switch provided by `@mui/material`.

### Example
```jsx
import React from 'react';
import DashboardChart from './DashboardChart';

function DashboardPage() {
  return (
    <div>
      <h1>Dashboard</h1>
      <DashboardChart />
    </div>
  );
}
```
### Decomission
#### Purpose
The `DecommissionedItemsDataTable` subcomponent is a part of the `components` directory and is responsible for displaying a data table of decommissioned items. It provides a clear overview of various attributes of decommissioned items, including their ID, product name, decommission date, license number, and product type.

#### Usage
- This subcomponent is typically used within the application's user interface to present a list of decommissioned items.

#### Structure
- The `DecommissionedItemsDataTable` subcomponent structure is as follows:

#### Data Fetching
- The subcomponent fetches data about decommissioned items from the server using the `getDecommissionedItems` function.
- The data is retrieved during the component's lifecycle using the `useEffect` hook.

#### Data Table
- A data grid is displayed using the `@mui/x-data-grid` library.
- The columns for the data grid are defined to include fields like ID, product name, decommission date, license number, and product type.

#### Product Type Status
- The "Product Type" column in the data table includes a rendered cell that distinguishes between "Software" and "Device" based on the product type, adding appropriate styling.

### Example
```jsx
import React from 'react';
import DecommissionedItemsDataTable from './DecommissionedItemsDataTable';

function DecommissionedItemsPage() {
  return (
    <div>
      <h1>Decommissioned Items</h1>
      <DecommissionedItemsDataTable />
    </div>
  );
}
```
### about to expire device 
#### Purpose
The `AboutToExpireDeviceDataTable` subcomponent is responsible for displaying a data table of devices that are about to expire. It provides a clear overview of device details including ID, name, company, license, cost, purchase date, expiry date, and status. The component fetches data about devices that are about to expire and presents it in a structured table format.

#### Usage
- This subcomponent is typically used within the application's user interface to provide information about devices that are close to their expiration date.

#### Structure
- The `AboutToExpireDeviceDataTable` subcomponent includes the following key features:

#### Data Fetching
- Data about devices that are about to expire is fetched from the server using the `getDevicesAboutToExpire` function.
- The data is retrieved during the component's lifecycle using the `useEffect` hook.

#### Data Table
- The subcomponent uses the `@mui/x-data-grid` library to display the data in a table format.
- The columns for the data grid are defined, including fields like ID, name, company, license, cost, purchase date, expiry date, and status.

#### Status
- The "Status" column in the data table indicates whether a device is expired or not and applies appropriate styling.

### Example
```jsx
import React from 'react';
import AboutToExpireDeviceDataTable from './AboutToExpireDeviceDataTable';

function AboutToExpireDevicesPage() {
  return (
    <div>
      <h1>About to Expire Devices</h1>
      <AboutToExpireDeviceDataTable />
    </div>
  );
}
