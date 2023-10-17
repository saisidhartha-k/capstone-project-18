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

