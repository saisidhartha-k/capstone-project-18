import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

export const getAboutExpiredData = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/software/getAboutExpired`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};
