import React from "react";
import "../../pages/pages.scss";
import AboutToExpireDataTable from "../../components/softwaredatatables/AboutToExpireSoftware";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

export const AboutToExpireSoftwareList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <AboutToExpireDataTable />
      </div>
    </div>
  );
};

export default AboutToExpireSoftwareList;
