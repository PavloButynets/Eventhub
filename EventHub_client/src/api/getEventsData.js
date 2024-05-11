import axios from "./axios";

const DATA_URL = "/search";

export const getEventsDataSearch = async (searchValue) => {
  const accessToken = localStorage.getItem("token");

  const authAxios = axios.create({
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
      ...(accessToken && { Authorization: `Bearer ${accessToken}` }),
    },
  });

  const response = await authAxios.get(DATA_URL, {
    params: {
      prompt: searchValue,
    },
  });
  return response.data;
};
