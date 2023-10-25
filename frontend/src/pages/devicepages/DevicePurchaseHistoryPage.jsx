import React from "react";
import '../../pages/pages.scss';
import DevicePurchasesDataTable from "../../components/devicedatatables/DevicePurchasesHistory";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

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
