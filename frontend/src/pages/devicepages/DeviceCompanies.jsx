import React from "react";
import '../../pages/pages.scss';
import DeviceCompaniesDataTable from "../../components/devicedatatables/DeviceCompanies";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";


export const DeviceCompaniesList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <h1>Device Companies</h1>
        <DeviceCompaniesDataTable />
      </div>
    </div>
  );
};

export default DeviceCompaniesList;
