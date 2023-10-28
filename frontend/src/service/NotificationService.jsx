import axios from 'axios';

const BASE_URL = 'http://localhost:8080/notification'; 

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

export const getNotifications = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getAll`, getHeaders());
    return response.data;
  } catch (error) {
    console.error('Error fetching notifications', error);
    throw error; 
  }
};
