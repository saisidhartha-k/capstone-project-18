import axios from "axios";

const BASE_URL = "http://localhost:8080/software";

const getToken = () => {
  const authState = localStorage.getItem("auth-state");
  if (authState) {
    const authStateObject = JSON.parse(authState);
    return authStateObject.token;
  }
  return null;
};
 export const getHeaders = () => {
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

export const getSoftwareExpiredData = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getExpired`, getHeaders());
    console.log("service");
    console.log(response);
    return response.data;
  } catch (error) {
    throw new Error("Error fetching data");
  }
};

export const getSoftwares = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/get`, getHeaders());
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error("Error fetching software data");
  }
};

export const getNotExpiredCount = async () => {
  try {
    const response = await axios.get(
      `${BASE_URL}/getNotExpiredCount`,
      getHeaders()
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching data");
  }
};

export const getExpiredCount = async () => {
  try {
    const response = await axios.get(
      `${BASE_URL}/getExpiredCount`,
      getHeaders()
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching data");
  }
};

export const addSoftware = async (softwareData) => {
  try {
    const response = await axios.post(
      `${BASE_URL}/addsoftware`,
      softwareData,
      getHeaders()
    );
    return response.data;
  } catch (error) {
    throw new Error("Error adding software");
  }
};

export const getExpired = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getExpired`, getHeaders());
    return response.data;
  } catch (error) {
    throw new Error("Error fetching expired software");
  }
};

export const getAboutExpired = async () => {
  try {
    const response = await axios.get(
      `${BASE_URL}/getAboutExpired`,
      getHeaders()
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching software about to expire");
  }
};

export const getSoftwareCompanies = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/softwarecompany/getcompanies",
      getHeaders()
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching companies");
  }
};

export const decommissionSoftware = async (id) => {
  try {
    await axios.delete(`${BASE_URL}/decomission/${id}`, getHeaders());
  } catch (error) {
    throw new Error("Error decommissioning software");
  }
};

export const fetchAssetCheck = async () => {
  try {
    const response = await axios.post(
      `${BASE_URL}/assetcheck`,
      {},
      getHeaders()
    );
    console.log(response);

    if (response.status === 200) {
      return response.data;
    } else {
      throw new Error("Failed to fetch asset check data");
    }
  } catch (error) {
    console.error("Error:", error);
    throw new Error("Failed to fetch asset check data");
  }
};

export const renewSoftware = async (id, softwareData) => {
  try {
    const response = await axios.post(
      `${BASE_URL}/renew/${id}`,
      softwareData,
      getHeaders()
    );
    return response.data;
  } catch (error) {
    throw new Error("Error renewing software");
  }
};
