import React from "react";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import "./Index.scss";
import SoftwareCompanies from "../../components/softwaredatatables/SoftwareCompanies";

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
