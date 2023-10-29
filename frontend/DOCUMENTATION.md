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
```

### AllDevicesDataTable

#### Purpose
The `AllDevicesDataTable` subcomponent is responsible for displaying a data table of all devices. It provides a clear overview of device details including ID, name, company, license, cost, purchase date, expiry date, and status. Additionally, it offers options to decommission devices and move them to the RMA (Return Merchandise Authorization) process.

#### Usage
- This subcomponent is typically used within the application's user interface to present a list of all devices and perform actions like decommissioning and RMA.

#### Structure
- The `AllDevicesDataTable` subcomponent includes the following key features:

#### Data Fetching
- Data about all devices is fetched from the server using the `getDevices` function.
- The data is retrieved during the component's lifecycle using the `useEffect` hook.

#### Data Table
- The subcomponent uses the `@mui/x-data-grid` library to display the data in a table format.
- The columns for the data grid are defined, including fields like ID, name, company, license, cost, purchase date, expiry date, and status.

#### Actions
- The subcomponent provides options to decommission devices and initiate the RMA process for selected devices.
- Modal dialogs are used to confirm decommissioning and gather RMA reason input.

### Example
```jsx
import React from 'react';
import AllDevicesDataTable from './AllDevicesDataTable';

function AllDevicesPage() {
  return (
    <div>
      <h1>All Devices</h1>
      <AllDevicesDataTable />
    </div>
  );
}
```


### DeviceCompaniesDataTable

#### Purpose
The `DeviceCompaniesDataTable` subcomponent is responsible for displaying a data table of device companies. It provides a structured overview of company details including ID, name, and description.

#### Usage
- This subcomponent is typically used within the application's user interface to present a list of device companies.

#### Structure
- The `DeviceCompaniesDataTable` subcomponent includes the following key features:

#### Data Fetching
- Data about device companies is fetched from the server using the `getAllDeviceCompanies` function.
- The data is retrieved during the component's lifecycle using the `useEffect` hook.

#### Data Table
- The subcomponent uses the `@mui/x-data-grid` library to display the data in a table format.
- The columns for the data grid are defined, including fields like ID, name, and description.

### Example
```jsx
import React from 'react';
import DeviceCompaniesDataTable from './DeviceCompaniesDataTable';

function DeviceCompaniesPage() {
  return (
    <div>
      <h1>Device Companies</h1>
      <DeviceCompaniesDataTable />
    </div>
  );
}
```

### ExpiredDeviceDataTable

#### Purpose
The `ExpiredDeviceDataTable` subcomponent is responsible for displaying a data table of devices with expiration information. It provides a structured overview of device details, including ID, name, company, license, cost, purchase date, expiry date, and status.

#### Usage
- This subcomponent is typically used within the application's user interface to present information about devices that have expired.

#### Structure
- The `ExpiredDeviceDataTable` subcomponent includes the following key features:

#### Data Fetching
- Data about devices with expiration information is fetched from the server using the `getDeviceExpiredData` function.
- The data is retrieved during the component's lifecycle using the `useEffect` hook.

#### Data Table
- The subcomponent uses the `@mui/x-data-grid` library to display the data in a table format.
- The columns for the data grid are defined, including fields like ID, name, company, license, cost, purchase date, expiry date, and status.

#### Status
- The "Status" column in the data table indicates whether a device is expired or not and applies appropriate styling.

### Example
```jsx
import React from 'react';
import ExpiredDeviceDataTable from './ExpiredDeviceDataTable';

function ExpiredDevicesPage() {
  return (
    <div>
      <h1>Expired Devices</h1>
      <ExpiredDeviceDataTable />
    </div>
  );
}
```
### featured chart
#### Purpose
The `Featured` subcomponent is responsible for displaying featured data in the form of a pie chart. It allows users to toggle between software and device cost visualization and provides information about companies with the most and least money spent.

#### Usage
- This subcomponent is often placed on the application's dashboard or a dedicated feature page to showcase cost data in an engaging way.

#### Structure
- The `Featured` subcomponent offers the following key features:

#### Data Fetching and Visualization
- Data for the pie chart is fetched from the server based on the selected data type (software or device) using the `getSoftwares` and `getDevices` functions.
- The pie chart is generated using the `chart.js` library.

#### Toggle Functionality
- Users can toggle between software and device data visualization.
- The switch control enables the user to switch between "Show Software Costs" and "Show Device Costs" labels.

#### Company Information
- The subcomponent displays the name of the company with the most and least money spent.

### Example
```jsx
import React from 'react';
import Featured from './Featured';

function DashboardPage() {
  return (
    <div>
      <h1>Dashboard</h1>
      <Featured />
    </div>
  );
}
```

### Navbar

#### Purpose
The `Navbar` subcomponent serves as the application's navigation bar, offering links to various sections and functionalities. It also provides options to toggle notifications and log out.

#### Usage
- The `Navbar` subcomponent is typically placed at the top of the application interface to enable navigation and access to important features.

#### Structure
- The `Navbar` subcomponent includes the following key features:

#### Scrolling Behavior
- The navigation bar's appearance can change based on scrolling behavior.

#### Notifications
- Users can toggle the display of notifications.
- The icon changes to indicate whether notifications are active or muted.

#### Log Out
- Users can log out from the application.

### Example
```jsx
import React from 'react';
import Navbar from './Navbar';

function App() {
  // ...
  return (
    <div>
      <Navbar toggleNotifications={handleToggleNotifications} />
    </div>
  );
}
```

### AllNotificationsDataTable

#### Purpose
The `AllNotificationsDataTable` subcomponent is responsible for displaying a data table containing notifications. It presents information about product names, expiry dates, days left, messages, and product types (software or device).

#### Usage
- This subcomponent is often used to show a list of notifications, allowing users to stay informed about important events.

#### Structure
- The `AllNotificationsDataTable` subcomponent includes the following key features:

#### Data Fetching
- Notification data is fetched from the server using the `getNotifications` function.

#### Data Table
- The subcomponent uses the `@mui/x-data-grid` library to display the data in a table format.
- The columns for the data grid are defined, including fields like ID, product name, expiry date, days left, message, and product type.

### Example
```jsx
import React from 'react';
import AllNotificationsDataTable from './AllNotificationsDataTable';

function NotificationsPage() {
  return (
    <div>
      <h1>Notifications</h1>
      <AllNotificationsDataTable />
    </div>
  );
}
```
### RMA table
#### Purpose
The `RMADataTable` subcomponent is responsible for displaying a data table containing information about Return Merchandise Authorization (RMA). It allows users to return items from RMA.

#### Usage
- This subcomponent is often used to manage and monitor RMA-related activities within the application.

#### Structure
- The `RMADataTable` subcomponent includes the following key features:

#### Data Fetching
- RMA data is fetched from the server using the `getRMA` function.

#### Return Functionality
- Users can return items from RMA by clicking the "Return" button in the table.

#### Data Table
- The subcomponent uses the `@mui/x-data-grid` library to display the data in a table format.
- The columns for the data grid include fields like ID, item name, reason, request date, actions (return button), and product type (software or device).

### Example
```jsx
import React from 'react';
import RMADataTable from './RMADataTable';

function RMAManagementPage() {
  return (
    <div>
      <h1>RMA Management</h1>
      <RMADataTable />
    </div>
  );
}
```


### Sidebar

#### Purpose
The `Sidebar` subcomponent serves as the application's sidebar navigation, providing quick access to various sections and functionalities.

#### Usage
- The `Sidebar` subcomponent is typically placed on the side of the application interface to enable navigation and access to different sections.

#### Structure
- The `Sidebar` subcomponent includes the following key features:

#### Logo
- The application's logo is displayed at the top of the sidebar.

#### Navigation
- The sidebar provides links to various sections, including the main dashboard, software-related menus, device-related menus, and others.
- Each section has an associated icon for quick identification.

### Example
```jsx
import React from 'react';
import Sidebar from './Sidebar';

function App() {
  // ...
  return (
    <div>
      <Sidebar />
      {/* Other components and content */}
    </div>
  );
}
```


### SoftwarePurchasesDataTable

#### Purpose
The `SoftwarePurchasesDataTable` subcomponent is responsible for displaying a data table containing information about software purchases. It presents details about license numbers, software names, software costs, and software companies.

#### Usage
- This subcomponent is often used to track and manage software purchases within the application.

#### Structure
- The `SoftwarePurchasesDataTable` subcomponent includes the following key features:

#### Data Fetching
- Software purchase data is fetched from the server using the `getAllSoftwareOrders` function.

#### Data Table
- The subcomponent uses the `@mui/x-data-grid` library to display the data in a table format.
- The columns for the data grid include fields like ID, license number, software name, software cost, and software company.

### Example
```jsx
import React from 'react';
import SoftwarePurchasesDataTable from './SoftwarePurchasesDataTable';

function SoftwarePurchaseHistoryPage() {
  return (
    <div>
      <h1>Software Purchase History</h1>
      <SoftwarePurchasesDataTable />
    </div>
  );
}
```