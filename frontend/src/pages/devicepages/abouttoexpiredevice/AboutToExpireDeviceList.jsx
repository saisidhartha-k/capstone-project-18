import React from "react";
import "./Styles.scss";
import AboutToExpireDeviceDataTable from "../../../components/devicedatatables/AboutToExpireDevices";
import { Sidebar } from "../../../components/sidebar/Sidebar";
import Navbar from "../../../components/navbar/Navbar";

export const AboutToExpireDeviceList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <AboutToExpireDeviceDataTable />
      </div>
    </div>
  );
};

export default AboutToExpireDeviceList;