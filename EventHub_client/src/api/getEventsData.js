import axios from './axios';

const DATA_URL = "/search";

export const getEventsDataSearch = async (searchValue) => {
  const authToken = localStorage.getItem('token');

  const response = await axios.get(DATA_URL, {
    params: {
      prompt: searchValue
    },

  });
  return response.data;
};
