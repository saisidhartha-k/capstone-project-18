import React, { useEffect, useState } from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { getAllDevicePurchases } from '../../service/DevicePurchaseService';

const columns = [
  { field: "id", headerName: "ID", width: 30 },
  { field: "purchaseDate", headerName: "Purchase Date", width: 150 }, // Changed "purchase Date" to "Purchase Date"
  {
    field: "expiryDate", // Changed "expirydate" to "expiryDate"
    headerName: "Expiry Date", // Changed "expiry Date" to "Expiry Date"
    width: 150,
    valueGetter: (params) => params.row.devicePurchaseId.device.expiryDate, // Changed "softwarePurchaseId" to "devicePurchaseId" and "software" to "device"
  },
  {
    field: "licenseNumber",
    headerName: "License Number", // Changed "licenseNumber" to "License Number"
    width: 150,
    valueGetter: (params) => params.row.devicePurchaseId.licenseNumber, // Changed "softwarePurchaseId" to "devicePurchaseId"
  },
  {
    field: "deviceName", // Changed "software name" to "deviceName"
    headerName: "Device Name", // Changed "software name" to "Device Name"
    width: 170,
    valueGetter: (params) => params.row.devicePurchaseId.device.name, // Changed "softwarePurchaseId" to "devicePurchaseId" and "software" to "device"
  },
  {
    field: "deviceCost", // Changed "software cost" to "deviceCost"
    headerName: "Device Cost", // Changed "software cost" to "Device Cost"
    width: 150,
    valueGetter: (params) => params.row.devicePurchaseId.device.cost, // Changed "softwarePurchaseId" to "devicePurchaseId" and "software" to "device"
  },
  {
    field: "deviceCompany", // Changed "device company" to "deviceCompany"
    headerName: "Device Company", // Changed "device company" to "Device Company"
    width: 200,
    valueGetter: (params) => params.row.devicePurchaseId.device.company.name, // Changed "devicePurchaseId" to "devicePurchaseId"
  },
];

export default function DevicePurchasesDataTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const result = await getAllDevicePurchases(); // Changed the function to fetch device purchases
        const dataWithCustomIds = result.map((row, index) => ({
          ...row,
          id: `${index}`,
        }));
        setData(dataWithCustomIds);
      } catch (error) {
        console.error('Error fetching device data', error);
      }
    }

    fetchData();
  }, []);

  return (
    <div className='datatable'>
      <DataGrid
        rows={data}
        columns={columns}
        pageSize={5}
      />
    </div>
  );
}
