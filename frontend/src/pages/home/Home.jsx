import React from "react";
import { Sidebar } from "../../components/sidebar/Sidebar";
import "./home.scss";
import Navbar from "../../components/navbar/Navbar";
import Widget from "../../components/widgets/Widget";
import Featured from "../../components/featured/Featured";
import Table from "../../components/table/Table";

export const Home = () => {
  return (
    <div className="home">
      <Sidebar />
      <div className="homeContainer">
        <Navbar />
        <div className="widgets">
          <Widget
            title="Softwares with Vaid license"
            endpoint="http://localhost:8080/software/getNotExpiredCount"
            className="green-tint"
          />
          <Widget
            title="Expired Softwares"
            endpoint="http://localhost:8080/software/getExpiredCount"
            className="red-tint" 
          />
          <Widget
            title="Softwares About to Expire"
            endpoint="http://localhost:8080/software/getAboutExpiredCount"
            className="yellow-tint"
          />
        </div>
        <div className="widgets">
        <Widget
            title="Devices with Vaid license"
            //endpoint="http://localhost:8080/software/getNotExpiredCount"
            className="green-tint"
          />
          <Widget
            title="Expired Devices"
            //endpoint="http://localhost:8080/software/getExpiredCount"
            className="red-tint" 
          />
          <Widget
            title="Devices About to Expire"
            //endpoint="http://localhost:8080/software/getAboutExpiredCount"
            className="yellow-tint"
          />
        </div>
        <div className="charts">
            <Featured/>
            {/* <Featured/> */}
        </div>
        <div className="listContainer">
          <div className="listTitle">
            <Table/>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Home;
