import axios from './axios';
import { jwtDecode } from 'jwt-decode'

export const getCheckbuttonsEvents = async (
    is_my_events, 
    is_joined_events,
    is_pending_events,
    is_archive_events ) => {

  const authToken = localStorage.getItem('token');

  const user = jwtDecode(authToken);
  const user_id = user.id;
  const DATA_URL = '/events/checkbox-filter';

  const response = await axios.post(DATA_URL, {
    user_id,
    is_my_events,
    is_joined_events,
    is_pending_events,
    is_archive_events
    },{
      headers:{'Content-Type':'application/json'},
    });

  return response.data;
};