import React from 'react'
import { Sidebar } from '../../components/sidebar/Sidebar'
import Navbar from '../../components/navbar/Navbar'
import AddSoftware from '../../components/add/AddSoftware'

const addSoftwarePage = () => {
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

export default addSoftwarePage