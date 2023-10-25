import axios from 'axios';

const BASE_URL = 'http://localhost:8080/devicehistory';

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

export const getAllDevicePurchases = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/devices`, getHeaders());
    console.log(response);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching device orders');
  }
};
