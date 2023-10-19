import React from "react";
import { Sidebar } from "../../../components/sidebar/Sidebar";
import Navbar from "../../../components/navbar/Navbar";
import "./softwarelist.scss";
import AllSoftwareDataTable from "../../../components/softwaredatatables/AllSoftwares";

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
