import React, { useEffect, useState } from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { getDevices, decommissionDevice } from '../../service/DeviceService';

export default function AllDevicesDataTable() {
  const [data, setData] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [deviceIdToDecommission, setDeviceIdToDecommission] = useState(null);

  const handleDecommission = async (deviceId) => {
    setDeviceIdToDecommission(deviceId);
    setShowModal(true);
  };

  const confirmDecommission = async () => {
    if (deviceIdToDecommission) {
      await decommissionDevice(deviceIdToDecommission);
      setData((prevData) =>
        prevData.filter((device) => device.id !== deviceIdToDecommission)
      );
    }
    setShowModal(false);
  };

  const closeConfirmationModal = () => {
    setShowModal(false);
  };

  useEffect(() => {
    async function fetchData() {
      try {
        const result = await getDevices();
        setData(result);
        console.log('data', data);
      } catch (error) {
        console.error('Error fetching device data', error);
      }
    }

    fetchData();
  }, []);

  const columns = [
    { field: 'id', headerName: 'ID', width: 60 },
    { field: 'name', headerName: 'Name', width: 190 },
    {
      field: 'company',
      headerName: 'Company',
      width: 150,
      valueGetter: (params) => params.row.company.name,
    },
    { field: 'licenseNumber', headerName: 'License', width: 110 },
    { field: 'cost', headerName: 'Cost', width: 70 },
    { field: 'purchaseDate', headerName: 'Purchase Date', width: 120 },
    { field: 'expiryDate', headerName: 'Expiry Date', width: 120 },
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
    {
      field: 'actions',
      headerName: 'Actions',
      width: 120,
      renderCell: (params) => {
        return (
          <>
            <button onClick={() => handleDecommission(params.row.id)}>
              Decommission
            </button>
            {showModal && (
              <div className='modal-overlay'>
                <div className='modal-content'>
                  <h2>Do you want to decommission this device?</h2>
                  <button onClick={confirmDecommission}>Yes</button>
                  <button onClick={closeConfirmationModal}>No</button>
                </div>
              </div>
            )}
          </>
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
      />
    </div>
  );
}
