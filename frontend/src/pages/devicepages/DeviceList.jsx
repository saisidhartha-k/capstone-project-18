import React from "react";
import '../../pages/pages.scss';
import AllDevicesDataTable from "../../components/devicedatatables/AllDevices";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";


export const AllDevicesList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <AllDevicesDataTable />
      </div>
    </div>
  );
};

export default AllDevicesList;
