import React, { useEffect, useState } from "react";
import { Sidebar } from "../../components/sidebar/Sidebar";
import "./home.scss";
import Navbar from "../../components/navbar/Navbar";
import Widget from "../../components/widgets/Widget";
import Featured from "../../components/featured/Featured";
import Table from "../../components/table/Table";
import DashoardChart from "../../components/dashboardchart/DashoardChart";
import TaskAltIcon from "@mui/icons-material/TaskAlt";
import DangerousIcon from "@mui/icons-material/Dangerous";
import ReportIcon from "@mui/icons-material/Report";
import { ToastContainer, toast } from "react-toastify";
import { fetchAssetCheck } from "../../service/SoftwareService";
import "react-toastify/dist/ReactToastify.css";


export const Home = () => {
  const [notifications, setNotifications] = useState([]); 
  const [showNotifications, setShowNotifications] = useState(false); 

  useEffect(() => {
    const notificationInterval = setInterval(() => {
      if (showNotifications) {
        fetchDataForNotifications();
      }
    }, 5000);

    return () => {
      clearInterval(notificationInterval);
    };
  }, [showNotifications]);

  const handleMuteUnmute = (notification) => {
    const mutedNotifications = JSON.parse(localStorage.getItem("mutedNotifications")) || [];

    if (mutedNotifications.includes(notification)) {
      // Unmute the notification
      const updatedMutedNotifications = mutedNotifications.filter((item) => item !== notification);
      localStorage.setItem("mutedNotifications", JSON.stringify(updatedMutedNotifications));
    } else {
      // Mute the notification
      mutedNotifications.push(notification);
      localStorage.setItem("mutedNotifications", JSON.stringify(mutedNotifications));
    }
  };

  const isMuted = (notification) => {
    const mutedNotifications = JSON.parse(localStorage.getItem("mutedNotifications")) || [];
    return mutedNotifications.includes(notification);
  };

  const fetchDataForNotifications = async () => {
    try {
      const data = await fetchAssetCheck();

      if (Array.isArray(data)) {
        setNotifications(data);
        if (showNotifications) {
          displayNotifications(data);
        }
      } else {
        console.error("Invalid data format received from the API.");
      }
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const displayNotifications = (data) => {
    data.forEach((message) => {
      if (!isMuted(message)) {
        const isMutedNotification = isMuted(message);
        toast(
          <div>
            <span>{message}</span>
            <button onClick={() => handleMuteUnmute(message)}>
              {isMutedNotification ? "Unmute" : "Mute"}
            </button>
          </div>,
          {
            autoClose: 5000,
          }
        );
      }
    });
  };

  const toggleNotifications = () => {
    setShowNotifications((prevShowNotifications) => !prevShowNotifications);
  };

  return (
    <div className="home">
      <Sidebar />
      <div className="homeContainer">
        <Navbar toggleNotifications={toggleNotifications} />
        <div className="widgets">
          <Widget
            title="Softwares with Valid License"
            endpoint="http://localhost:8080/software/getNotExpiredCount"
            className="green-tint"
            value="http://localhost:8080/software/percentageNotExpired"
            icon={<TaskAltIcon style={{ color: "green" }} />}
          />

          <Widget
            title="Expired Softwares"
            endpoint="http://localhost:8080/software/getExpiredCount"
            className="red-tint"
            value="http://localhost:8080/software/percentageExpired"
            icon={<DangerousIcon style={{ color: "red" }} />}
          />

          <Widget
            title="Softwares About to Expire"
            endpoint="http://localhost:8080/software/getAboutExpiredCount"
            className="yellow-tint"
            value="http://localhost:8080/software/percentageAboutToExpire"
            icon={<ReportIcon style={{ color: "orange" }} />}
          />
        </div>
        <div className="widgets">
          <Widget
            title="Devices with Vaid license"
            endpoint="http://localhost:8080/device/notExpiredCount"
            className="green-tint"
            value="http://localhost:8080/device/percentageNotExpired"
            icon={<TaskAltIcon style={{ color: "green" }} />}
          />
          <Widget
            title="Expired Devices"
            endpoint="http://localhost:8080/device/expiredCount"
            className="red-tint"
            value="http://localhost:8080/device/percentageExpired"
            icon={<DangerousIcon style={{ color: "red" }} />}
          />
          <Widget
            title="Devices About to Expire"
            endpoint="http://localhost:8080/device/aboutToExpireCount"
            className="yellow-tint"
            value="http://localhost:8080/device/percentageAboutToExpire"
            icon={<ReportIcon style={{ color: "orange" }} />}
          />
        </div>
        <div className="charts">
          <Featured />
          <DashoardChart />
        </div>

        <div className="listContainer">
          <div className="listTitle">
            <Table />
          </div>
        </div>
      </div>
      <ToastContainer
        position="top-right"
        autoClose={2000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
        style={{ position: "fixed", top: "60px", right: "0" }}
      />
    </div>
  );
};
export default Home;
