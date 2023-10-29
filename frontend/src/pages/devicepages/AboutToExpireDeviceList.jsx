import React from "react";
import '../../pages/pages.scss';
import AboutToExpireDeviceDataTable from "../../components/devicedatatables/AboutToExpireDevices";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";


export const AboutToExpireDeviceList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <h1>About To Expire Devices</h1>
        <AboutToExpireDeviceDataTable />
      </div>
    </div>
  );
};

export default AboutToExpireDeviceList;
