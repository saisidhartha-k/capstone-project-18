import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { getSoftwareExpiredData } from "../../service/SoftwareService";
import { getDeviceExpiredData } from "../../service/DeviceService";
import Switch from "@mui/material/Switch";
import { styled } from "@mui/material/styles";
import IconButton from "@mui/material/IconButton";
import DevicesIcon from "@mui/icons-material/Devices";
import TerminalIcon from "@mui/icons-material/Terminal";
import { green, red } from "@mui/material/colors";

// Define the common columns
const columns = [
  { field: "id", headerName: "ID", width: 50 },
  { field: "name", headerName: "Name", width: 200 },
  {
    field: "company",
    headerName: "Company",
    width: 200,
    valueGetter: (params) => params.row.company.name,
  },
  { field: "licenseNumber", headerName: "License Number", width: 200 },
  { field: "cost", headerName: "Cost", width: 150 },
  { field: "expiryDate", headerName: "Expiry Date", width: 150 },
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

export default function DataTable() {
  const [data, setData] = useState([]);
  const [showSoftwareData, setShowSoftwareData] = useState(true);
  const [switchColor, setSwitchColor] = useState(green);

  const fetchData = () => {
    if (showSoftwareData) {
      getSoftwareExpiredData()
        .then((result) => {
          setData(result);
        })
        .catch((error) => {
          console.error("Error fetching software data:", error);
        });
    } else {
      getDeviceExpiredData()
        .then((result) => {
          setData(result);
        })
        .catch((error) => {
          console.error("Error fetching device data:", error);
        });
    }
  };

  useEffect(() => {
    fetchData();
  }, [showSoftwareData]);

  const toggleData = () => {
    setShowSoftwareData((prev) => !prev);
    setSwitchColor((prev) => (prev === green ? red : green));
  };

  const MaterialUISwitch = styled(Switch)(({ theme }) => ({
    padding: 6,
    position: "relative",
    "& .MuiIconButton-label": {
      position: "absolute",
      left: "50%",
      top: "50%",
      transform: "translate(-50%, -50%)",
      display: "flex",
      flexDirection: "row",
      "& .MuiIconButton-edgeEnd": {
        marginLeft: 0,
      },
    },
  }));

  const tableHeading = showSoftwareData ? "Expired Software" : "Expired Device";

  return (
    <div>
      <h1>{tableHeading}</h1>
      <div style={{ display: "flex", alignItems: "center" }}>
        <MaterialUISwitch
          checked={showSoftwareData}
          onChange={toggleData}
          sx={{
            "&.Mui-checked": { color: switchColor },
            "&.Mui-checked + .MuiSwitch-track": { backgroundColor: switchColor },
          }}
        />
        <div style={{ marginLeft: "10px" }}>
          {showSoftwareData ? "Show Expired Device" : "Show Expired Software"  }
        </div>
      </div>
      <div style={{ height: 400, width: "100%" }}>
        <DataGrid
          rows={data}
          columns={columns}
          initialState={{
            pagination: {
              paginationModel: { page: 0, pageSize: 5 },
            },
          }}
          pageSize={5}
        />
      </div>
    </div>
  );
}