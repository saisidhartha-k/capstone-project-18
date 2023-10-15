import React, { useEffect, useState } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts'; // Change the import to LineChart and Line
import { getSoftwares } from '../../service/SoftwareService';
import './dashboardchart.scss'; // Import the SCSS file

function DashboardChart() {
  const [softwareData, setSoftwareData] = useState([]);

  useEffect(() => {
    getSoftwares() // Call your service method here
      .then((response) => {
        setSoftwareData(response);
      })
      .catch((error) => {
        console.error('Error fetching software data:', error);
      });
  }, []);

  const softwareChartData = softwareData.map((software) => ({
    name: software.name,
    numberOfEmployees: software.numberOfEmployees,
  }));

  return (
    <div className='chart'>
      <h2>Software usage</h2>
      <ResponsiveContainer width='100%' height={400}>
        <LineChart data={softwareChartData}> 
          <CartesianGrid strokeDasharray='3 3' />
          <XAxis dataKey='name' />
          <YAxis />
          <Tooltip />
          <Line type='monotone' dataKey='numberOfEmployees' stroke='#8884d8' fill='#8884d8' /> 
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}

export default DashboardChart;
