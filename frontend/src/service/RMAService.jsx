import axios from 'axios';

const BASE_URL = 'http://localhost:8080/RMA'; // Adjust the base URL as needed

export const moveSoftwareToRma = async (softwareId, rma) => {
  try {
    await axios.post(`${BASE_URL}/moveSoftware/${softwareId}`, rma);
  } catch (error) {
    throw new Error('Error moving software to RMA');
  }
};

export const moveDeviceToRma = async (deviceId, rma) => {
    try {
      await axios.post(`${BASE_URL}/moveDevice/${deviceId}`, rma);
    } catch (error) {
      throw new Error('Error moving device to RMA');
    }
  };

  export const putBackSoftwareFromRma = async (id) => {
    try {
      await axios.post(`${BASE_URL}/putBackSoftware/${id}`);
    } catch (error) {
      throw new Error('Error putting back software from RMA');
    }
  };

  export const putBackDeviceFromRma = async (id) => {
    try {
      await axios.post(`${BASE_URL}/putBackDevice/${id}`);
    } catch (error) {
      throw new Error('Error putting back device from RMA');
    }
  };

  export const getRMA = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/getRma`);
      return response.data;
    } catch (error) {
      throw new Error('Error fetching RMA data');
    }
  };
  