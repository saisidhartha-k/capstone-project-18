import React, { useEffect, useRef, useState } from "react";
import "./featured.scss";
import Chart from "chart.js/auto";
import { getSoftwares } from "../../service/SoftwareService";
import { getDevices } from "../../service/DeviceService";
import Switch from "@mui/material/Switch"; // Import the MUI Switch component
import FormControlLabel from "@mui/material/FormControlLabel";
import Tooltip from "@mui/material/Tooltip"; // Import the Tooltip component


const Featured = () => {
  const chartRef = useRef(null);
  const chartInstanceRef = useRef(null);
  const [mostSpentCompany, setMostSpentCompany] = useState("");
  const [leastSpentCompany, setLeastSpentCompany] = useState("");
  const [dataType, setDataType] = useState("software");
  const [data, setData] = useState(null);

  const toggleData = () => {
    if (dataType === "software") {
      setDataType("device");
    } else {
      setDataType("software");
    }
  };

  useEffect(() => {
    // Fetch and display data based on the selected data type
    const fetchData = async () => {
      let dataResponse;
      if (dataType === "software") {
        dataResponse = await getSoftwares();
      } else {
        dataResponse = await getDevices();
      }

      const companyData = dataResponse;
      const companyCosts = {};
      companyData.forEach((company) => {
        const companyName = company.company.name;
        const cost = company.cost;
        if (companyCosts[companyName]) {
          companyCosts[companyName] += cost;
        } else {
          companyCosts[companyName] = cost;
        }
      });

      const companyNames = Object.keys(companyCosts);
      const costs = Object.values(companyCosts);

      const chartData = {
        labels: companyNames,
        datasets: [
          {
            data: costs,
            backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56"],
          },
        ],
      };

      if (chartInstanceRef.current) {
        chartInstanceRef.current.destroy();
      }

      const ctx = chartRef.current.getContext("2d");
      const newChartInstance = new Chart(ctx, {
        type: "pie",
        data: chartData,
      });

      chartInstanceRef.current = newChartInstance;

      const maxCost = Math.max(...costs);
      const minCost = Math.min(...costs);

      const mostSpentCompanyIndex = costs.indexOf(maxCost);
      const leastSpentCompanyIndex = costs.indexOf(minCost);

      setMostSpentCompany(companyNames[mostSpentCompanyIndex]);
      setLeastSpentCompany(companyNames[leastSpentCompanyIndex]);
      setData(chartData);
    };

    fetchData();
  }, [dataType]);

  return (
    <div className="featured">
      <h2 className="chart-heading">
        {dataType === "software" ? "Software Costs" : "Device Costs"}
      </h2>
      <div className="chart-container">
        <canvas ref={chartRef} width={300} height={300}></canvas>
      </div>
      <div className="company-info">
        <p className="info-text">
          Company with the most money spent: {mostSpentCompany}
        </p>
        <p className="info-text">
          Company with the least money spent: {leastSpentCompany}
        </p>
      </div>
      <Tooltip title={`Press to show ${dataType === "software" ? "Device" : "Software"} Costs`} arrow>
        <FormControlLabel
          control={
            <Switch
              checked={dataType === "device"}
              onChange={toggleData}
              color="primary"
            />
          }
        />
      </Tooltip>
    </div>
  );
};

export default Featured;
