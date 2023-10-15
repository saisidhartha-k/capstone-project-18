// softwareService.js
import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

export const getSoftwareExpiredData = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/software/getExpired`);
    console.log('service')
    console.log(response)
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};

export const getSoftwares = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/software/get`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching software data');
  }
};

export const getNotExpiredCount = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/software/getNotExpiredCount`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};

export const getExpiredCount = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/software/getExpiredCount`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};
