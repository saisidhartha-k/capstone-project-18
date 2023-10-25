import React from "react";
import '../../pages/pages.scss';

import SoftwarePurchasesDataTable from "../../components/SoftwareComponents/software_purchase_components/SoftwarePurchaseHistory";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

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
