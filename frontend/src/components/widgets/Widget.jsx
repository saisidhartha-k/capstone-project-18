import React from 'react'
import './widget.scss'
import DoneIcon from '@mui/icons-material/Done';

const Widget = () => {
  return (
    <div className='widget'>
        <div className='left'>
        <span className='title'>all softwares</span>
        <span className='counter'> 212</span>
        <span className='link'>see more</span> 
    </div>
    <div className='right'>
        <div className='percentage positive'>
           <span>200</span> 
           <span><DoneIcon className='icon'/></span>

        </div>
    </div>
        </div>
  )
}

export default Widget