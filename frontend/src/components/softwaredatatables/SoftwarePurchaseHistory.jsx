import React, { useEffect, useState } from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { getAllSoftwareOrders } from '../../service/SoftwarePurchaseService';

const columns = [
  { field: "id", headerName: "ID", width: 100 },
  {
    field: "licenseNumber",
    headerName: "LicenseNumber",
    width: 200,
    valueGetter: (params) => params.row.softwarePurchaseId.licenseNumber,
  },
  {
    field: "software name",
    headerName: "Software Name",
    width: 250,
    valueGetter: (params) => params.row.softwarePurchaseId.software.name,
  },
  {
    field: "software cost",
    headerName: "Software Cost",
    width: 170,
    valueGetter: (params) => params.row.softwarePurchaseId.software.cost,
  },
  {
    field: "software company",
    headerName: "Software Company",
    width: 200,
    valueGetter: (params) => params.row.softwarePurchaseId.software.company.name,
  },
  

];

export default function SoftwarePurchasesDataTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const result = await getAllSoftwareOrders();
        const dataWithCustomIds = result.map((row, index) => ({
          ...row,
          id: `${index}`,
        }));
        setData(dataWithCustomIds);
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
