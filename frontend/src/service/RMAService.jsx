import axios from 'axios';

const BASE_URL = 'http://localhost:8080/RMA'; 

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

export const moveSoftwareToRma = async (softwareId, rma) => {
  try {
    await axios.post(`${BASE_URL}/moveSoftware/${softwareId}`, rma, getHeaders());
  } catch (error) {
    throw new Error('Error moving software to RMA');
  }
};

export const moveDeviceToRma = async (deviceId, rma) => {
    try {
      await axios.post(`${BASE_URL}/moveDevice/${deviceId}`, rma, getHeaders());
    } catch (error) {
      throw new Error('Error moving device to RMA');
    }
  };

  export const putBackSoftwareFromRma = async (id) => {
    try {
      await axios.post(`${BASE_URL}/putBackSoftware/${id}`, getHeaders());
    } catch (error) {
      throw new Error('Error putting back software from RMA');
    }
  };

  export const putBackDeviceFromRma = async (id) => {
    try {
      await axios.post(`${BASE_URL}/putBackDevice/${id}`, getHeaders());
    } catch (error) {
      throw new Error('Error putting back device from RMA');
    }
  };

  export const getRMA = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/getRma`, getHeaders());
      return response.data;
    } catch (error) {
      throw new Error('Error fetching RMA data');
    }
  };
  