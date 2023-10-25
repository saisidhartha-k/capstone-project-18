import axios from 'axios';

const BASE_URL = 'http://localhost:8080/devicehistory';

export const getAllDevicePurchases = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/devices`);
    console.log(response);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching device orders');
  }
};
