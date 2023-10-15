import React, { useState, useEffect } from "react";
import "./widget.scss";
import DoneIcon from "@mui/icons-material/Done";
import axios from "axios";
import {
  CircularProgressBar,
  CircularProgressbar,
  buildStyles,
} from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";

const Widget = ({ title, endpoint, className }) => {
  const [data, setData] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(endpoint);
        setData(response.data);
      } catch (error) {
        console.error(
          `An error occurred while fetching data for ${title}:`,
          error
        );
      }
    };

    fetchData();
  }, [endpoint, title]);

  return (
    <div className={`widget ${className}`}>
      <div className="left">
        <span className="title">{title}</span>
        <span className="counter">{data}</span>
        <span className="link">See more</span>
      </div>
      <div className="right">
        <div className="small-progress-bar">
          <span>
            <CircularProgressbar
              value={50}
              text={`${50}%`}
              strokeWidth={5}
              styles={buildStyles({
                textSize: "27px",
              })}
            />
            <br />
            <br />
            <br />
          </span>
            <DoneIcon className="icon" />
        </div>
      </div>
    </div>
  );
};

export default Widget;
