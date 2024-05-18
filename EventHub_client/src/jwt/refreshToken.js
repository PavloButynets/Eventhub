import axios from "../api/axios";

export const refreshToken = async (token) => {
  const DATA_URL = "/authentication/refreshToken";

  const accessToken = localStorage.getItem("token");

  try {
    const response = await axios.post(
      DATA_URL,
      {
        token,
      },
      {
        headers: {
          "Content-Type": "application/json",
          ...(accessToken && { Authorization: `Bearer ${accessToken}` }),
        },
      }
    );

    const newAccessToken = response?.data?.accessToken;
    const expiryDate = response?.data?.expiryDate;

    localStorage.setItem("token", newAccessToken);
    localStorage.setItem("expDate", expiryDate);

    return response.data;
  } catch (error) {
    throw error;
  }
};
