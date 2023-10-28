import React from "react";
import "./sidebar.scss";
import TerminalIcon from "@mui/icons-material/Terminal";
import SpaceDashboardIcon from "@mui/icons-material/SpaceDashboard";
import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import AddToPhotosIcon from "@mui/icons-material/AddToPhotos";
import { Link } from "react-router-dom";
import WorkHistoryIcon from '@mui/icons-material/WorkHistory';
import DvrIcon from '@mui/icons-material/Dvr';

export const Sidebar = () => {
  return (
    <div className="sidebar">
      <div className="top">
        <span className="logo">LLM</span>
      </div>
      <div className="center">
        <ul>
          <p className="title"> Main </p>
          <li>
            <Link to="/home">
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
              <TerminalIcon className="icon" />
              <span>Software Companies</span>
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
              <WorkHistoryIcon className="icon" />
              <span>Software Purchases</span>
            </Link>
          </li>

          <p className="title"> Device menu </p>
          <li>
            <Link to="/device">
              <DvrIcon className="icon" />
              <span>Devices</span>
            </Link>
          </li>

          <li>
            <Link to="/device/expireddevice">
              <DvrIcon className="icon" />
              <span>Expired Devices</span>
            </Link>
          </li>
          <li>
            <Link to="/device/abouttoexpireDevice">
              <DvrIcon className="icon" />
              <span>About To Expire </span>
            </Link>
          </li>

          <li>
            <Link to="/device/devicecompanies">
              <DvrIcon className="icon" />
              <span>Device Companies</span>
            </Link>
          </li>

          <li>
            <Link to="/device/adddevice">
              <AddToPhotosIcon className="icon" />
              <span>Add Device</span>
            </Link>
          </li>

          <li>
            <Link to="/device/devicepurchases">
              <WorkHistoryIcon className="icon" />
              <span>Device Purchases</span>
            </Link>
          </li>

          <p className="title"> Others </p>
          <li>
          <Link to="/rma">
            <NotificationsActiveIcon className="icon" />
            <span>RMA</span>
          </Link>
          </li>
          <li>
          <Link to="/decomissioneditems">
            <NotificationsActiveIcon className="icon" />
            <span>decomissioned items</span>
          </Link>
          </li>
        </ul>
      </div>
    </div>
  );
};
