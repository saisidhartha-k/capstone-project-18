import React, { useEffect, useState } from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { getAboutExpired } from '../../service/SoftwareService';
import { getDeviceExpiredData, getDevicesAboutToExpire } from '../../service/DeviceService';

const columns = [
  { field: "id", headerName: "ID", width: 60 },
  { field: "name", headerName: "Name", width: 200 },
  {
    field: "company",
    headerName: "Company",
    width: 150,
    valueGetter: (params) => params.row.company.name,
  },
  { field: "licenseNumber", headerName: "License", width: 120 },
  { field: "cost", headerName: "Cost", width: 100 },
  { field: 'purchaseDate', headerName: 'Purchase Date', width: 120 },
  { field: "expiryDate", headerName: "Expiry Date", width: 120 },
  {
    field: "isExpired",
    headerName: "Status",
    width: 100,
    renderCell: (params) => {
      const today = new Date();
      const expiryDate = new Date(params.row.expiryDate);
      const isExpired = expiryDate <= today;
      const statusText = isExpired ? "Expired" : "Not Expired";
      const statusClass = isExpired ? "Expired" : "NotExpired";
      return (
        <div className={`cellWithStatus ${statusClass}`}>{statusText}</div>
      );
    },
  },
];

export default function ExpiredDeviceDataTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const result = await getDeviceExpiredData();
        setData(result);
        console.log('data', data);
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
        checkboxSelection
      />
    </div>
  );
}
