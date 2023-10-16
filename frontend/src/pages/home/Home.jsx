import React from "react";
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

export const Home = () => {
  return (
    <div className="home">
      <Sidebar />
      <div className="homeContainer">
        <Navbar />
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
    </div>
  );
};
export default Home;
