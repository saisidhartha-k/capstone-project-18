import React from "react";
import '../../pages/pages.scss';
import AllNotificationsDataTable from "../../components/notifications/NotificationDataTable";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

export const AllNotificationList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <AllNotificationsDataTable />
      </div>
    </div>
  );
};

export default AllNotificationList;
