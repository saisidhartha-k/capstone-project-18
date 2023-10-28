import React, { useEffect, useState } from 'react';
import './rmadatatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { getRMA, putBackSoftwareFromRma, putBackDeviceFromRma } from '../../service/RMAService';

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

  const handleReturnButtonClick = async (id, productType) => {
    try {
      if (productType === null) {
        await putBackSoftwareFromRma(id);
      } else  {
        await putBackDeviceFromRma(id);
      }
      const rmaData = await getRMA();
      setData(rmaData);
    } catch (error) {
      console.error('Error putting back item from RMA', error);
    }
  };

  const columns = [
    { field: 'id', headerName: 'ID', width: 60 },
    {
      field: 'name',
      headerName: 'Item Name',
      width: 150,
      valueGetter: (params) => {
        if (params.row.software) {
          return params.row.software.name; 
        } else if (params.row.device) {
          return params.row.device.name; 
        } else {
          return ''; 
        }
      },
    },
    { field: 'reason', headerName: 'Reason', width: 200 },
    { field: 'requestDate', headerName: 'Request Date', width: 120 },
    {
      field: "actions",
      headerName: "Actions",
      width: 180,
      renderCell: (params) => {
        return (
          <div className="action-buttons">
            <button id='handleReturnButtonClick' onClick={() => handleReturnButtonClick(params.row.id, params.row.device)}>
              Return
            </button>
          </div>
        );
      },
    },
    {
      field: "productType",
      headerName: "Product Type",
      width: 120,
      renderCell: (params) => {
        const productType = params.row.device ? 'Device' : 'Software';
        const productTypeClass = productType === 'Device' ? 'Device' : 'Software';
        return (
          <div className={`cellWithStatus ${productTypeClass}`}>
            {productType}
          </div>
        );
      },
    },
  ];

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
