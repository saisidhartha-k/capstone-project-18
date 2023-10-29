/* The code is importing various dependencies and modules that are required for the `Widget` component
to function properly. */
import React, { useState, useEffect } from "react";
import "./widget.scss";
import axios from "axios";
import { CircularProgressbar, buildStyles } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import { getHeaders } from "../../service/SoftwareService";
import ReorderIcon from '@mui/icons-material/Reorder';

const Widget = ({ title, endpoint, className, value, icon }) => {
  const [dataFromEndpoint, setDataFromEndpoint] = useState(null);
  const [dataFromValue, setDataFromValue] = useState(null);

  useEffect(() => {
    const fetchDataFromEndpoint = async () => {
      try {
        const endpointResponse = await axios.get(endpoint, getHeaders());
        setDataFromEndpoint(endpointResponse.data);
      } catch (error) {
        console.error(
          `An error occurred while fetching data from 'endpoint' for ${title}:`,
          error
        );
      }
    };


    const fetchDataFromValue = async () => {
      try {
        const valueResponse = await axios.get(value, getHeaders());
        setDataFromValue(valueResponse.data);
      } catch (error) {
        console.error(
          `An error occurred while fetching data from 'value' for ${title}:`,
          error
        );
      }
    };

    fetchDataFromEndpoint();
    fetchDataFromValue();
  }, [endpoint, value, title]);

  return (
    <div className={`widget ${className}`}>
      <div className="left">
        <span className="title">{title}</span>
        <span className="counter">{dataFromEndpoint}</span>
        <br />
        <div  style={{ textDecoration: 'none' }}>
          <ReorderIcon/>
        </div>
      </div>
      <div className="right">
        <div className="small-progress-bar">
          <span>
            <CircularProgressbar
              value={dataFromValue}
              text={`${dataFromValue}%`}
              strokeWidth={4}
              styles={buildStyles({
                textSize: "25px",
              })}
            />
            <br />
            <br />
            <br />
          </span>
          {icon}
        </div>
      </div>
    </div>
  );
};

export default Widget;
