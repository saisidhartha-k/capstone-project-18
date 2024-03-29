import React, { useEffect, useState } from "react";
import "./datatable.scss";
import { DataGrid } from "@mui/x-data-grid";
import {
  decommissionSoftware,
  getSoftwares,
} from "../../service/SoftwareService";
import { moveSoftwareToRma } from "../../service/RMAService";
import Modal from "react-modal";

export default function AllSoftwareDataTable() {
  const [data, setData] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [softwareIdToDecommission, setSoftwareIdToDecommission] =
    useState(null);
  const [selectedSoftwareId, setSelectedSoftwareId] = useState(null);
  const [rmaData, setRmaData] = useState({ reason: "" });
  const [modalIsOpen, setModalIsOpen] = useState(false);

  const openModal = () => {
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
  };

  const handleDecommission = async (softwareId) => {
    setSoftwareIdToDecommission(softwareId);
    setShowModal(true);
  };

  const confirmDecommission = async () => {
    if (softwareIdToDecommission) {
      await decommissionSoftware(softwareIdToDecommission);
      setData((prevData) =>
        prevData.filter((software) => software.id !== softwareIdToDecommission)
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
        const result = await getSoftwares();
        setData(result);
      } catch (error) {
        console.error("Error fetching software data", error);
      }
    }

    fetchData();
  }, []);

  const handleRMA = (softwareId) => {
    setSelectedSoftwareId(softwareId);
    openModal(); 
  };

  const saveRMA = async () => {
    try {
      await moveSoftwareToRma(selectedSoftwareId, rmaData);

      setData((prevData) =>
        prevData.filter((software) => software.id !== selectedSoftwareId)
      );

      closeModal(); 
      setRmaData({ reason: "" }); 
    } catch (error) {
      console.error("Error moving software to RMA", error);
    }
  };

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
            <button id="decommissionButton" onClick={() => handleDecommission(params.row.id)}>
              Decommission
            </button>
            <button id="RMAbutton" onClick={() => handleRMA(params.row.id)}>RMA</button>
            {showModal && (
              <div className="modal-overlay">
                <div className="modal-content">
                  <h2>Do you want to decommission this device?</h2>
                  <button id="confirmDecommission" onClick={confirmDecommission}>Yes</button>
                  <button id="closeConfirmationModal" onClick={closeConfirmationModal}>No</button>
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
      params.row.software &&
      params.row.software.available === "NOT_AVAILABLE"
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
        shouldCloseOnOverlayClick={true} 
      >
        <h2>Enter RMA Reason</h2>
        <input
          id="RMAReason"
          type="text"
          value={rmaData.reason}
          onChange={(e) => setRmaData({ reason: e.target.value })}
        />
        <button id="saveRMA" onClick={saveRMA}>Save</button>
        <button id="cancelRMA"  onClick={closeModal}>Cancel</button>
      </Modal>
    </div>
  );
}
