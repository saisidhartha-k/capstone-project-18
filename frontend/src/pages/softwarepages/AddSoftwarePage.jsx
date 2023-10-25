import React from 'react'
import AddSoftware from '../../components/add/AddSoftware'
import '../../pages/pages.scss';
import { Sidebar } from '../../components/sidebar/Sidebar';
import Navbar from '../../components/navbar/Navbar';


const AddNewSoftware = () => {
  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <AddSoftware/>
        </div>

        </div>
  )
}

export default AddNewSoftware