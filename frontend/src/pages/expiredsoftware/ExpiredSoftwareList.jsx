import React from "react";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import '../../pages/pages.scss';
import ExpiredDataTable from "../../components/softwaredatatables/ExpiredSoftware";
import { Container } from "@mui/material";

export const ExpiredSoftwareList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <ExpiredDataTable />
      </div>
    </div>
  );
};

export default ExpiredSoftwareList;
