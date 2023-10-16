import React from "react";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import "./Index.scss";
import ExpiredDataTable from "../../components/softwaredatatables/ExpiredSoftware";
import { Container } from "@mui/material";
import AboutToExpireDataTable from "../../components/softwaredatatables/AboutToExpireSoftware";

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
