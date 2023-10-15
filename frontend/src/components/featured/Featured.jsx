import React, { useEffect, useRef, useState } from 'react';
import './featured.scss';
import Chart from 'chart.js/auto';
import { getSoftwares } from '../../service/SoftwareService';

const Featured = () => {
  const chartRef = useRef(null);
  const chartInstanceRef = useRef(null);
  const [mostSpentCompany, setMostSpentCompany] = useState('');
  const [leastSpentCompany, setLeastSpentCompany] = useState('');

  useEffect(() => {
    getSoftwares()
      .then((response) => {
        const softwareData = response;
        console.log(response);
        const companyNames = softwareData.map((software) => software.company.name);
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

        const maxCost = Math.max(...costs);
        const minCost = Math.min(...costs);

        const mostSpentCompanyIndex = costs.indexOf(maxCost);
        const leastSpentCompanyIndex = costs.indexOf(minCost);

        setMostSpentCompany(companyNames[mostSpentCompanyIndex]);
        setLeastSpentCompany(companyNames[leastSpentCompanyIndex]);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []);

  return (
    <div className="featured">
      <h2 className="chart-heading">Software Costs</h2>
      <div className="chart-container">
        <canvas ref={chartRef} width={300} height={300}></canvas>
      </div>
      <div className="company-info">
        <p className="info-text">Company with the most money spent: {mostSpentCompany}</p>
        <p className="info-text">Company with the least money spent: {leastSpentCompany}</p>
      </div>
    </div>
  );
};

export default Featured;
