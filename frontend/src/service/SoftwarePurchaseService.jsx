import axios from 'axios';

const BASE_URL = 'http://localhost:8080/orders';

export const getAllSoftwareOrders = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/softwares`);
      console.log(response);
      return response.data;
    } catch (error) {
      throw new Error('Error fetching software orders');
    }
  };