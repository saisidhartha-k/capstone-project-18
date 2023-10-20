import React from "react";

import "./Index.scss";
import { Sidebar } from "../../../components/sidebar/Sidebar";
import Navbar from "../../../components/navbar/Navbar";
import SoftwareCompanies from "../../../components/softwaredatatables/SoftwareCompanies";

export const SoftwareComapniesList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <SoftwareCompanies />
      </div>
    </div>
  );
};

export default SoftwareComapniesList;