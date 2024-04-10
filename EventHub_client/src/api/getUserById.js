import axios from "./axios";

export const getUserById = async (userId) => {
  try {
    const accessToken = localStorage.getItem("token");

    const authAxios = axios.create({
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers": "content-type",
        "Access-Control-Allow-Credentials": "true",
      },
    });
    const response = await authAxios.get(`users/${userId}`);
    return response.data;
  } catch (error) {
    console.log("Error getting user with Id: ", error);
  }
};
