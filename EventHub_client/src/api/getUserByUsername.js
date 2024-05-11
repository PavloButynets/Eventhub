import axios from "./axios";

export const getUserByUsername = async (username) => {
  const accessToken = localStorage.getItem("token");

  const authAxios = axios.create({
    headers: {
      ...(accessToken && { Authorization: `Bearer ${accessToken}` }),
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  const response = await authAxios.get(`users/${username}/profile`);
  return response.data;
};
