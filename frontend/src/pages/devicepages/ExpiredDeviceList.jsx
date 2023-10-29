import React from "react";
import '../../pages/pages.scss';
import ExpiredDeviceDataTable from "../../components/devicedatatables/ExpiredDevices";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

export const ExpiredDeviceList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <h1>Expired Device</h1>
        <ExpiredDeviceDataTable />
      </div>
    </div>
  );
};

export default ExpiredDeviceList;
