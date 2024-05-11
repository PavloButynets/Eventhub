import axios from "./axios";

const CATEGORIES_URL = "categories";

export const getCategories = async () => {
  const accessToken = localStorage.getItem("token");

  const authAxios = axios.create({
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
      ...(accessToken && { Authorization: `Bearer ${accessToken}` }),
    },
  });

  try {
    const response = await authAxios.get(CATEGORIES_URL);
    return response.data;
  } catch (error) {
    console.log("Error getting categories", error);
  }
};
