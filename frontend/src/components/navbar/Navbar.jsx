import React, { useState, useEffect } from "react";
import "./navbar.scss";
import { Tooltip } from "@mui/material";
import NotificationsIcon from "@mui/icons-material/Notifications";
import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import { Link } from "react-router-dom";
import LogoutIcon from "@mui/icons-material/Logout";

const Navbar = ({ toggleNotifications }) => {
  const [isScrolled, setIsScrolled] = useState(false);
  const [showNotifications, setShowNotifications] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 0) {
        setIsScrolled(true);
      } else {
        setIsScrolled(false);
      }
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('auth-state');
    window.location.href = 'http://localhost:3000/';

  };

  return (
    <div className={`navbar ${isScrolled ? "fixed" : ""}`}>
      <div className="wrapper">
        <div className="items">
          <div className="item">
            <Link to="/notification">
              <span>ALL Notifications</span>
            </Link>
          </div>
          <div className="item">
            <Tooltip
              title={
                showNotifications ? "Mute Notifications" : "Show Notifications"
              }
              arrow
            >
              <div
                onClick={() => {
                  setShowNotifications(!showNotifications);
                  toggleNotifications();
                }}
              >
                {showNotifications ? (
                  <NotificationsActiveIcon className="icon" />
                ) : (
                  <NotificationsIcon className="icon" />
                )}
              </div>
            </Tooltip>
          </div>
          <Tooltip
            title="Logout">
          <div className="item" onClick={handleLogout}>
            <LogoutIcon />
          </div>
          </Tooltip>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
