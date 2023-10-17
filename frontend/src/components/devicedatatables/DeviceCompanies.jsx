import React, { useEffect, useState } from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { getAllDeviceCompanies } from '../../service/DeviceService';


const columns = [
  { field: "id", headerName: "ID", width: 60 },
  { field: "name", headerName: "Name", width: 200 },

  { field: "description", headerName: "description", width: 400 },
];

export default function DeviceCompaniesDataTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const result = await getAllDeviceCompanies();
        setData(result);
        console.log('data', data);
      } catch (error) {
        console.error('Error fetching device company data', error);
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
