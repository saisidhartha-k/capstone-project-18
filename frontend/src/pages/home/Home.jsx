import React from 'react'
import { Sidebar } from '../../components/sidebar/Sidebar';
import "./home.scss"
import Navbar from '../../components/navbar/Navbar';
import Widget from '../../components/widgets/Widget';

export const Home = () => {
  return (
    <div className='home'>
        <Sidebar/>
        <div className='homeContainer'>
        <Navbar/>
        <div className='widgets'>
        <Widget/>
        <Widget/>
        <Widget/>
        </div>
        <div className='widgets'>
        <Widget/>
        <Widget/>
        <Widget/>
        </div>
        </div>
    </div>
  )
}
export default Home;