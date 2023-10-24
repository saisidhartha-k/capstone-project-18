import React, { useEffect, useState } from 'react';
import './rmadatatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { getRMA } from '../../service/RMAService';

const columns = [
  { field: 'id', headerName: 'ID', width: 60 },
  {
    field: 'name',
    headerName: 'Item Name',
    width: 150,
    valueGetter: (params) => {
      if (params.row.software) {
        return params.row.software.name; // Display software name if software is not null
      } else if (params.row.device) {
        return params.row.device.name; // Display device name if device is not null
      } else {
        return ''; // Return an empty string if both software and device are null
      }
    },
  },
  { field: 'reason', headerName: 'Reason', width: 200 },
  { field: 'requestDate', headerName: 'Request Date', width: 120 },
];

export default function RMADataTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const rmaData = await getRMA();
        console.log(rmaData);
        setData(rmaData);
      } catch (error) {
        console.error('Error fetching RMA data', error);
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
