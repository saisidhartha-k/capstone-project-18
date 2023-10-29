/* The code is importing various dependencies and modules that are required for the `Widget` component
to function properly. */
import React, { useState, useEffect } from "react";
import "./widget.scss";
import axios from "axios";
import { CircularProgressbar, buildStyles } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import { getHeaders } from "../../service/SoftwareService";
import ReorderIcon from '@mui/icons-material/Reorder';

/**
 * Widget component displays a customizable widget with a title, data from an endpoint, and an icon.
 *
 * @component
 * @param {Object} props - The component's props.
 * @param {string} props.title - The title of the widget.
 * @param {string} props.endpoint - The endpoint for fetching data.
 * @param {string} props.className - The CSS class name for styling the widget.
 * @param {string} props.value - The value used in the widget.
 * @param {ReactNode} props.icon - The icon element to display in the widget.
 * @returns {JSX.Element} Widget component.
 */
const Widget = ({ title, endpoint, className, value, icon }) => {
  const [dataFromEndpoint, setDataFromEndpoint] = useState(null);
  const [dataFromValue, setDataFromValue] = useState(null);

 /**
   * Fetch data from the specified endpoint and store it in the component's state.
   * @function
   * @async
   * @returns {Promise<void>}
   */
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

/**
   * Fetch data from the specified endpoint and store it in the component's state.
   * @function
   * @async
   * @returns {Promise<void>}
   */
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

 /* The `return` statement in the `Widget` component is responsible for rendering the JSX (JavaScript
 XML) code that represents the structure and content of the component. */
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
