import React from "react";

import "./devicepurchases.scss";

import { Sidebar } from "../../../components/sidebar/Sidebar";
import Navbar from "../../../components/navbar/Navbar";
import DevicePurchasesDataTable from "../../../components/devicedatatables/DevicePurchasesHistory";

export const DevicePurchaseHistoryList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <DevicePurchasesDataTable />
      </div>
    </div>
  );
};

export default DevicePurchaseHistoryList;
