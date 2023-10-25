import React, { useEffect, useState } from "react";
import "./datatable.scss";
import { DataGrid } from "@mui/x-data-grid";
import { getDevices, decommissionDevice } from "../../service/DeviceService";
import Modal from "react-modal"; 
import { moveDeviceToRma } from "../../service/RMAService";

export default function AllDevicesDataTable() {
  const [data, setData] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [deviceIdToDecommission, setDeviceIdToDecommission] = useState(null);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [rmaData, setRmaData] = useState({ reason: "" });
  const [selectedDeviceId, setSelectedDeviceId] = useState(null);

  const openModal = () => {
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
  };

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

  const saveRMA = async () => {
    try {
      await moveDeviceToRma(selectedDeviceId, rmaData);

      // After successful RMA, update the UI as needed
      setData((prevData) =>
        prevData.filter((device) => device.id !== selectedDeviceId)
      );

      closeModal(); // Close the modal
      setRmaData({ reason: "" }); // Reset the reason input
      setSelectedDeviceId(null); // Reset the selectedDeviceId
    } catch (error) {
      console.error("Error moving device to RMA", error);
    }
  };

  const handleRMA = (deviceId) => {
    setSelectedDeviceId(deviceId);
    openModal(); // Open the modal when "RMA" button is clicked
  };

  useEffect(() => {
    async function fetchData() {
      try {
        const result = await getDevices();
        setData(result);
      } catch (error) {
        console.error("Error fetching device data", error);
      }
    }

    fetchData();
  }, []);

  const columns = [
    { field: "id", headerName: "ID", width: 7 },
    { field: "name", headerName: "Name", width: 190 },
    {
      field: "company",
      headerName: "Company",
      width: 150,
      valueGetter: (params) => params.row.company.name,
    },
    { field: "licenseNumber", headerName: "License", width: 100 },
    { field: "cost", headerName: "Cost", width: 70 },
    { field: "purchaseDate", headerName: "Purchase Date", width: 120 },
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
    {
      field: "actions",
      headerName: "Actions",
      width: 180,
      renderCell: (params) => {
        return (
          <div className="action-buttons">
            <button onClick={() => handleDecommission(params.row.id)}>
              Decommission
            </button>
            <button onClick={() => handleRMA(params.row.id)}>RMA</button>
            {showModal && (
              <div className="modal-overlay">
                <div className="modal-content">
                  <h2>Do you want to decommission this device?</h2>
                  <button onClick={confirmDecommission}>Yes</button>
                  <button onClick={closeConfirmationModal}>No</button>
                </div>
              </div>
            )}
          </div>
        );
      },
    },
  ];

  const getRowStyle = (params) => {
    if (
      params.row.device &&
      params.row.device.available === "NOT_AVAILABLE"
    ) {
      return { display: "none" };
    } 
    return {};
  };

  return (
    <div className="datatable">
      <DataGrid
        rows={data}
        columns={columns}
        pageSize={5}
        getRowStyle={getRowStyle}
      />

      <Modal
        isOpen={modalIsOpen}
        onRequestClose={closeModal}
        contentLabel="RMA Reason Modal"
      >
        <h2>Enter RMA Reason</h2>
        <input
          type="text"
          value={rmaData.reason}
          onChange={(e) => setRmaData({ reason: e.target.value })}
        />
        <button onClick={saveRMA}>Save</button>
        <button onClick={closeModal}>Cancel</button>
      </Modal>
    </div>
  );
}
