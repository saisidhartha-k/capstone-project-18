import React from "react";

import '../../pages/pages.scss';

import SoftwareCompanies from "../../components/softwaredatatables/SoftwareCompanies";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

export const SoftwareComapniesList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <h1>Software Companies</h1>
        <SoftwareCompanies />
      </div>
    </div>
  );
};

export default SoftwareComapniesList;
