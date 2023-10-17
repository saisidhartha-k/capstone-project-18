import React from "react";

import "./softwarepurchasehistory.scss";
import { Sidebar } from "../../../components/sidebar/Sidebar";
import Navbar from "../../../components/navbar/Navbar";
import SoftwarePurchasesDataTable from "../../../components/SoftwareComponents/software_purchase_components/SoftwarePurchaseHistory";

export const SoftwarePurchaseHistoryList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <SoftwarePurchasesDataTable />
      </div>
    </div>
  );
};

export default SoftwarePurchaseHistoryList;
