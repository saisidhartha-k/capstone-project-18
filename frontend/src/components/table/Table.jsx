import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { getAboutExpiredData } from "../../service/SoftwareService";

const columns = [
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
  { field: "isExpired", headerName: "Expired", width: 100 },
];

export default function DataTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    getAboutExpiredData()
      .then((result) => {
        setData(result);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  return (
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
        checkboxSelection
      />
    </div>
  );
}
