import React, { useEffect, useState } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import { getSoftwares } from '../../service/SoftwareService';
import { getDevices } from '../../service/DeviceService'; // Import the service for device data
import './dashboardchart.scss';
import Switch from '@mui/material/Switch';
import FormControlLabel from '@mui/material/FormControlLabel';

function DashboardChart() {
  const [softwareData, setSoftwareData] = useState([]);
  const [dataType, setDataType] = useState('software');

  useEffect(() => {
    fetchData();
  }, [dataType]); // Fetch data when dataType changes

  const fetchData = () => {
    if (dataType === 'software') {
      getSoftwares()
        .then((response) => {
          setSoftwareData(response);
        })
        .catch((error) => {
          console.error('Error fetching software data:', error);
        });
    } else if (dataType === 'device') {
      getDevices()
        .then((response) => {
          setSoftwareData(response);
        })
        .catch((error) => {
          console.error('Error fetching device data:', error);
        });
    }
  };

  const softwareChartData = softwareData.map((software) => ({
    name: software.name,
    numberOfEmployees: software.numberOfEmployees,
  }));

  return (
    <div className="chart">
      <h2>{dataType === 'software' ? 'Software Usage' : 'Device Usage'}</h2>
        <FormControlLabel
          control={
            <Switch
              checked={dataType === 'device'}
              onChange={() => setDataType(dataType === 'device' ? 'software' : 'device')}
              color="primary"
            />
          }
          label={`Show ${dataType === 'device' ? 'Software' : 'Device'} Data`}
        />
      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={softwareChartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Line type="monotone" dataKey="numberOfEmployees" stroke="#8884d8" fill="#8884d8" />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}

export default DashboardChart;
