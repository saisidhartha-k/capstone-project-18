import React, { useEffect, useRef, useState } from 'react';
import './featured.scss';
import Chart from 'chart.js/auto';
import axios from 'axios';

const Featured = () => {
  const chartRef = useRef(null);
  const chartInstanceRef = useRef(null);
  const [chartData, setChartData] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/software/get')
      .then((response) => {
        const softwareData = response.data;
        console.log(response);
        const companyNames = softwareData.map((software) => software.companyName);
        const costs = softwareData.map((software) => software.cost);

        const data = {
          labels: companyNames,
          datasets: [
            {
              data: costs,
              backgroundColor: [
                '#FF6384', '#36A2EB', '#FFCE56',
              ],
            },
          ],
        };

        if (chartInstanceRef.current) {
          chartInstanceRef.current.destroy();
        }

        const ctx = chartRef.current.getContext('2d');
        const newChartInstance = new Chart(ctx, {
          type: 'pie',
          data: data,
        });

        chartInstanceRef.current = newChartInstance;
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []);

  return (
    <div className="featured">
      <div className="chart-container">
        <canvas ref={chartRef} width={300} height={300}></canvas>
      </div>
    </div>
  );
};

export default Featured;
