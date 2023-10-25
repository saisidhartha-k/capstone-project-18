import React from "react";
import '../../pages/pages.scss';
import ExpiredDataTable from "../../components/softwaredatatables/ExpiredSoftware";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

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
