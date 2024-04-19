import axios from "./axios";
import getIdFromToken from "../jwt/getIdFromToken";

export const getCheckbuttonsEvents = async (
  is_my_events,
  is_joined_events,
  is_pending_events,
  is_archive_events
) => {
  const DATA_URL = "/events/checkbox-filter";

  const user_id = getIdFromToken();

  const response = await axios.post(
    DATA_URL,
    {
      user_id,
      is_my_events,
      is_joined_events,
      is_pending_events,
      is_archive_events,
    },
    {
      headers: { "Content-Type": "application/json" },
    }
  );

  return response.data;
};
