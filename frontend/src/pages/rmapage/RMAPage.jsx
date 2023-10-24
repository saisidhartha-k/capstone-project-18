import React from "react";
import "./rmapage.scss";
import RMADataTable from "../../components/rma/RmaDataTable";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

export const RMAList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <RMADataTable />
      </div>
    </div>
  );
};

export default RMAList;
