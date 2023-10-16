// softwareService.js
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/software';

export const getSoftwareExpiredData = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getExpired`);
    console.log('service')
    console.log(response)
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};

export const getSoftwares = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/get`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching software data');
  }
};

export const getNotExpiredCount = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getNotExpiredCount`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};

export const getExpiredCount = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getExpiredCount`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};

export const addSoftware = async (softwareData) => {
  try {
    const response = await axios.post(`${BASE_URL}/addsoftware`, softwareData);
    return response.data; 
  } catch (error) {
    throw new Error('Error adding software');
  }
};

export const getExpired = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getExpired`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching expired software');
  }
};

export const getAboutExpired = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getAboutExpired`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching software about to expire');
  }
};

export const getSoftwareCompanies = async () => {
  try {
    const response = await axios.get('http://localhost:8080/softwarecompany/getcompanies');
    return response.data;
  } catch (error) {
    throw new Error('Error fetching companies');
  }
};






