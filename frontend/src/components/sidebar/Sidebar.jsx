import React from "react";
import "./sidebar.scss";
import TerminalIcon from "@mui/icons-material/Terminal";
import SpaceDashboardIcon from "@mui/icons-material/SpaceDashboard";
import DevicesIcon from "@mui/icons-material/Devices";
import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import AddToPhotosIcon from "@mui/icons-material/AddToPhotos";
import { Link, Route } from "react-router-dom";

export const Sidebar = () => {
  return (
    <div className="sidebar">
      <div className="top">
        <span className="logo">logo</span>
      </div>
      <div className="center">
        <ul>
          <p className="title"> Main </p>
          <li>
            <Link to="/">
              <SpaceDashboardIcon className="icon" />
              <span>Dashboard</span>
            </Link>
          </li>
          <p className="title"> Software Menu </p>
          <li>
            <Link to="/software">
              <TerminalIcon className="icon" />
              <span>Softwares</span>
            </Link>
          </li>
          <li>
            <Link to="/software/expiredSoftware">
              <TerminalIcon className="icon" />
              <span>Expired Software</span>
            </Link>
          </li>
          <li>
            <Link to="/software/abouttoexpireSoftware">
              <TerminalIcon className="icon" />
              <span>About To Expire</span>
            </Link>
          </li>

          <li>
            <Link to="/software/softwarecompanies">
              <DevicesIcon className="icon" />
              <span>All Companies</span>
            </Link>
          </li>

          <li>
            <Link to="/software/addSoftware">
              <AddToPhotosIcon className="icon" />
              <span>Add Softwares</span>
            </Link>
          </li>

          <li>
            <Link to="/software/softwarepurchasehistory">
              <AddToPhotosIcon className="icon" />
              <span>Software Purchases</span>
            </Link>
          </li>

          <p className="title"> Device menu </p>
          <li>
            <Link to="/device">
              <DevicesIcon className="icon" />
              <span>Devices</span>
            </Link>
          </li>

          <li>
            <Link to="/device/expireddevice">
              <DevicesIcon className="icon" />
              <span>Expired Devices</span>
            </Link>
          </li>
          <li>
            <Link to="/device/abouttoexpireDevice">
              <DevicesIcon className="icon" />
              <span>About To Expire </span>
            </Link>
          </li>

          <li>
            <Link to="/device/devicecompanies">
              <DevicesIcon className="icon" />
              <span>All Device Companies</span>
            </Link>
          </li>

          <p className="title"> Others </p>

          <li>
            <NotificationsActiveIcon className="icon" />
            <span>Notifications</span>
          </li>
        </ul>
      </div>
    </div>
  );
};
