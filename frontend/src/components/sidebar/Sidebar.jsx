import React from 'react'
import "./sidebar.scss"
import TerminalIcon from '@mui/icons-material/Terminal';
import SpaceDashboardIcon from '@mui/icons-material/SpaceDashboard';
import DevicesIcon from '@mui/icons-material/Devices';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';
import AddToPhotosIcon from '@mui/icons-material/AddToPhotos';

export const Sidebar = () => {
  return (
    <div className='sidebar'>
        <div className='top'>
            <span className='logo'>logo</span>
        </div>
        <div className='center'>
            <ul>
                <p className="title"> Main </p>
                <li>
                <SpaceDashboardIcon className='icon'/>
                    <span>Dashboard</span>
                </li>
                <p className="title"> Software Menu </p>
                <li>
                    <TerminalIcon className='icon'/>
                    <span>Softwares</span>
                </li>
                <li>
                    <AddToPhotosIcon className='icon'/>
                    <span>Add Softwares</span>
                </li>
                <p className="title"> Device menu </p>
                <li>
                    <DevicesIcon className='icon'/>
                    <span>Devices</span>
                </li>
                <p className="title"> Others </p>

                <li>
                    <NotificationsActiveIcon className='icon'/>
                    <span>Notifications</span>
                </li>
            </ul>
        </div>
        {/* <div className='bottom'>
            
            <div className='colorOption'></div>
            <div className='colorOption'></div>
        </div> */}

    </div>
    
  )
}
