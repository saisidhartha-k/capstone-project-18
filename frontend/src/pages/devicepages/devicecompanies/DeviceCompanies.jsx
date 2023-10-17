import React from "react";
import "./Styles.scss";
import DeviceCompaniesDataTable from "../../../components/devicedatatables/DeviceCompanies";
import { Sidebar } from "../../../components/sidebar/Sidebar";
import Navbar from "../../../components/navbar/Navbar";

export const DeviceCompaniesList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <DeviceCompaniesDataTable />
      </div>
    </div>
  );
};

export default DeviceCompaniesList;
