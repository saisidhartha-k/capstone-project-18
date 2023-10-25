import axios from 'axios';

const BASE_URL = 'http://localhost:8080/device';

const getToken = () => {
  const authState = localStorage.getItem("auth-state");
  if (authState) {
    const authStateObject = JSON.parse(authState);
    return authStateObject.token;
  }
  return null;
};
const getHeaders = () => {
  const token = getToken();
  if (token) {
    return {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
  }
  return {};
};

export const getDeviceExpiredData = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getExpired` ,getHeaders());
    console.log('service')
    console.log(response)
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};

export const getDevices = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/get`, getHeaders());
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching device data');
  }
};

export const getDevicesAboutToExpire = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getAboutExpired`, getHeaders());
    return response.data;
  } catch (error) {
    throw new Error('Error fetching devices about to expire');
  }
};

export const getAllDeviceCompanies = async () => {
  try {
    const response = await axios.get('http://localhost:8080/devicecompany/deviceCompanies', getHeaders());
    console.log(response);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching device companies');
  }
};

export const addDevice = async (device) => {
  try {
    const response = await axios.post(`${BASE_URL}/adddevice`, device, getHeaders());
    console.log(response);
    return response.data;
  } catch (error) {
    throw new Error('Error adding device');
  }
};

export const decommissionDevice = async (id) => {
  try {
    const response = await axios.delete(`${BASE_URL}/decomissionDevice/${id}`, getHeaders());
    console.log(response);
    return response.status === 200;
  } catch (error) {
    throw new Error('Error decommissioning device');
  }
};

export const renewDevice = async (id, deviceDto) => {
  try {
    const response = await axios.post(`${BASE_URL}/renew/${id}`, deviceDto, getHeaders());
    console.log(response);
    return response.data; 
  } catch (error) {
    throw new Error('Error renewing device');
  }
};

export const deviceAssetCheck = async () => {
  try {
    const response = await axios.post(`${BASE_URL}/assetcheck`, getHeaders());
    console.log(response);
    return response.data;
  } catch (error) {
    throw new Error('Error checking asset');
  }
};




