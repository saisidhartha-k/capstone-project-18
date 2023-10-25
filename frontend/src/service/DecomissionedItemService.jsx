import axios from 'axios';

const BASE_URL = 'http://localhost:8080/decomssioneditems'; 

export const getDecommissionedItems = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/get`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching decommissioned items');
  }
};
