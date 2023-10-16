import React from "react";
import { Sidebar } from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import "./softwarelist.scss";
import DataTable from "../../components/softwaredatatables/allSoftwares";
import { Container } from "@mui/material";

export const List = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <DataTable />
      </div>
    </div>
  );
};

export default List;
