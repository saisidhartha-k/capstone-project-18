import React, { useEffect, useState } from "react";
import "./notificationdatatable.scss";
import { DataGrid } from "@mui/x-data-grid";
import { getNotifications } from "../../service/NotificationService";

export default function AllNotificationsDataTable() {
    const [data, setData] = useState([]);
  
    useEffect(() => {
      async function fetchData() {
        try {
          const result = await getNotifications();
          setData(result);
        } catch (error) {
          console.error('Error fetching notifications', error);
        }
      }
  
      fetchData();
    }, []);
  
    const columns = [
      { field: 'id', headerName: 'ID', width: 60 },
      { field: 'productName', headerName: 'Product Name', width: 190 },
      { field: 'expiryDate', headerName: 'Expiry Date', width: 120 },
      { field: 'numberOfDaysLeft', headerName: 'Days Left', width: 100 },
      {
        field: 'message',
        headerName: 'Message',
        width: 460,
        renderCell: (params) => {
          return (
            <div className="message-cell">
              <textarea readOnly rows="2" cols="50" value={params.value} />
            </div>
          );
        },
      },      {
        field: "productTypeStatus",
        headerName: "Product Type",
        width: 120,
        renderCell: (params) => {
          const productType = params.row.productType;
          const productTypeClass = productType === "SOFTWARE" ? "Software" : "Device";
          return (
            <div className={`cellWithStatus ${productTypeClass}`}>{productType}</div>
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