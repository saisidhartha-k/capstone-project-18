import React from 'react'
import DeviceForm from '../../components/add/AddDevice'
import '../../pages/pages.scss';
import { Sidebar } from '../../components/sidebar/Sidebar';
import Navbar from '../../components/navbar/Navbar';


const AddNewDevice = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <DeviceForm/>
        </div>

        </div>
  )
}

export default AddNewDevice;