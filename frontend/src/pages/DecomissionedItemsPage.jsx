import React from "react";
import './pages.scss';
import DecomissionedItemsDataTable from "../components/decomissioneditems/DecomissionedItemsTable";
import { Sidebar } from "../components/sidebar/Sidebar";
import Navbar from "../components/navbar/Navbar";


export const DecomissionedItemsList = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <DecomissionedItemsDataTable />
      </div>
    </div>
  );
};

export default DecomissionedItemsList;
