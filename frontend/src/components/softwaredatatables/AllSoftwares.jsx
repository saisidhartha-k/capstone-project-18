import React, { useEffect, useState } from "react";
import "./datatable.scss";
import { DataGrid } from "@mui/x-data-grid";
import {
  decommissionSoftware,
  getSoftwares,
} from "../../service/SoftwareService";

export default function DataTable() {
  const [data, setData] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [softwareIdToDecommission, setSoftwareIdToDecommission] =
    useState(null);

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
    { field: "cost", headerName: "Cost", width: 70 },
    { field: "purchaseDate", headerName: "Purchase Date", width: 120 },
    { field: "expiryDate", headerName: "Expiry Date", width: 120 },
    {
      field: "isExpired",
      headerName: "Status",
      width: 100,
      renderCell: (params) => {
        const statusText = params.row.isExpired ? "Expired" : "Not Expired";
        const statusClass = params.row.isExpired ? "Expired" : "NotExpired";
        return (
          <div className={`cellWithStatus ${statusClass}`}>{statusText}</div>
        );
      },
    },
    {
      field: "actions",
      headerName: "Actions",
      width: 120,
      renderCell: (params) => {
        return (
          <>
            <button onClick={() => handleDecommission(params.row.id)}>
              Decommission
            </button>
            {showModal && (
              <div className="modal-overlay">
                <div className="modal-content">
                  <h2>Do you want to decommission this software?</h2>
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
    <div className="datatable">
      <DataGrid rows={data} columns={columns} pageSize={5} />
    </div>
  );
}
