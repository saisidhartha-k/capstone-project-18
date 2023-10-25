import React from "react";

import '../../pages/pages.scss';
import AllSoftwareDataTable from "../../components/softwaredatatables/AllSoftwares";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

export const SoftwareList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <AllSoftwareDataTable />
      </div>
    </div>
  );
};

export default SoftwareList;
