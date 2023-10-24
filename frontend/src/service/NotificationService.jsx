import axios from 'axios';

const BASE_URL = 'http://localhost:8080/notification'; 

export const getNotifications = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getAll`);
    return response.data;
  } catch (error) {
    // Handle errors, e.g., log the error or throw a custom error
    console.error('Error fetching notifications', error);
    throw error; // You can re-throw the error for further handling in your application
  }
};
