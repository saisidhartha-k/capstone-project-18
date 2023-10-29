import React, { useEffect, useState } from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { getSoftwareCompanies } from '../../service/SoftwareService';


const columns = [
  { field: "id", headerName: "ID", width: 60 },
  { field: "name", headerName: "Name", width: 200 },

  { field: "description", headerName: "description", width: 400 },
];

export default function SoftwareCompanies() {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const result = await getSoftwareCompanies();
        setData(result);
        console.log('data', data);
      } catch (error) {
        console.error('Error fetching software data', error);
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
