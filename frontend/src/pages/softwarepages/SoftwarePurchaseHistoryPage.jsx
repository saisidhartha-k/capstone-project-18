import React from "react";
import '../../pages/pages.scss';

import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import SoftwarePurchasesDataTable from "../../components/softwaredatatables/SoftwarePurchaseHistory";

export const SoftwarePurchaseHistoryList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <h1>Software Purchase History</h1>
        <SoftwarePurchasesDataTable />
      </div>
    </div>
  );
};

export default SoftwarePurchaseHistoryList;
