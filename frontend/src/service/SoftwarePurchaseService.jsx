import axios from "axios";

const BASE_URL = "http://localhost:8080/orders";

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

export const getAllSoftwareOrders = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/softwares`, getHeaders());
    console.log(response);
    return response.data;
  } catch (error) {
    throw new Error("Error fetching software orders");
  }
};
