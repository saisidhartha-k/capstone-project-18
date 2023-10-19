import axios from 'axios';

const BASE_URL = 'http://localhost:8080/device';

export const getDeviceExpiredData = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getExpired`);
    console.log('service')
    console.log(response)
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};

export const getDevices = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/get`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching device data');
  }
};

export const getDevicesAboutToExpire = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getAboutExpired`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching devices about to expire');
  }
};

export const getAllDeviceCompanies = async () => {
  try {
    const response = await axios.get('http://localhost:8080/devicecompany/deviceCompanies');
    console.log(response);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching device companies');
  }
};

export const addDevice = async (device) => {
  try {
    const response = await axios.post(`${BASE_URL}/adddevice`, device, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    console.log(response);
    return response.data;
  } catch (error) {
    throw new Error('Error adding device');
  }
};

export const decommissionDevice = async (id) => {
  try {
    const response = await axios.delete(`${BASE_URL}/decomissionDevice/${id}`);
    console.log(response);
    if (response.status === 200) {
      return true; 
    } else {
      return false;
    }
  } catch (error) {
    throw new Error('Error decommissioning device');
  }
};

export const fetchAssetCheck = async () => {
  try {
    const response = await axios.post(`${BASE_URL}/assetcheck`);
    console.log(response);

    if (response.status === 200) {
      return response.data;
    } else {
      throw new Error('Failed to fetch asset check data');
    }
  } catch (error) {
    console.error('Error:', error);
    throw new Error('Failed to fetch asset check data');
  }
};

