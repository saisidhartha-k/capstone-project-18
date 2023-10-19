import React from 'react'
import DeviceForm from '../../../components/add/AddDevice'
import Navbar from '../../../components/navbar/Navbar'
import { Sidebar } from '../../../components/sidebar/Sidebar'


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