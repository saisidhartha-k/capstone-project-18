import React, { useEffect, useState } from "react";
import "./decomissioneditemstable.scss";
import { DataGrid } from "@mui/x-data-grid";
import { getDecommissionedItems } from "../../service/DecomissionedItemService";

export default function DecomissionedItemsDataTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const result = await getDecommissionedItems();
        setData(result);
      } catch (error) {
        console.error("Error fetching decomissioned items", error);
      }
    }

    fetchData();
  }, []);

  const columns = [
    { field: "id", headerName: "ID", width: 60 },
    { field: "productName", headerName: "Product Name", width: 190 },
    { field: "expiryDate", headerName: "Expiry Date", width: 120 },
    { field: "decommissionDate", headerName: "Decommission Date", width: 120 },
    { field: "licenseNumber", headerName: "License Number", width: 120 },
    { field: "purchaseId", headerName: "Purchase ID", width: 120 },
    {
      field: "productTypeStatus",
      headerName: "Product Type",
      width: 120,
      renderCell: (params) => {
        const productType = params.row.productType;
        const productTypeClass =
          productType === "SOFTWARE" ? "Software" : "Device";
        return (
          <div className={`cellWithStatus ${productTypeClass}`}>
            {productType}
          </div>
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
