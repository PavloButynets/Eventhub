import axios from './axios';
import { jwtDecode } from 'jwt-decode'

export const getUserEvents = async () => {

  const authToken = localStorage.getItem('token');

  const user = jwtDecode(authToken);
  console.log(user);
  const DATA_URL = `/users/${user.id}/events`;

  const response = await axios.get(DATA_URL);
  return response.data;
};