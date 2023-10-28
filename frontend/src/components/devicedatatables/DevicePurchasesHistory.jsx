import React, { useEffect, useState } from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { getAllDevicePurchases } from '../../service/DevicePurchaseService';

const columns = [
  { field: "id", headerName: "ID", width: 30 },
  { field: "purchaseDate", headerName: "Purchase Date", width: 150 }, 
  {
    field: "expiryDate", 
    headerName: "Expiry Date", 
    width: 150,
    valueGetter: (params) => params.row.devicePurchaseId.device.expiryDate, 
  },
  {
    field: "licenseNumber",
    headerName: "License Number", 
    width: 150,
    valueGetter: (params) => params.row.devicePurchaseId.licenseNumber, 
  },
  {
    field: "deviceName", 
    headerName: "Device Name", 
    width: 170,
    valueGetter: (params) => params.row.devicePurchaseId.device.name, 
  },
  {
    field: "deviceCost", 
    headerName: "Device Cost", 
    width: 150,
    valueGetter: (params) => params.row.devicePurchaseId.device.cost, 
  },
  {
    field: "deviceCompany", 
    headerName: "Device Company", 
    width: 200,
    valueGetter: (params) => params.row.devicePurchaseId.device.company.name, 
  },
];

export default function DevicePurchasesDataTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const result = await getAllDevicePurchases(); 
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
