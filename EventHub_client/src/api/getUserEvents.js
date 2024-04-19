import axios from "./axios";
import getIdFromToken from "../jwt/getIdFromToken";

export const getUserEvents = async () => {
  const user_id = getIdFromToken();

  const DATA_URL = `/users/${user_id}/events`;

  const response = await axios.get(DATA_URL);
  return response.data;
};
