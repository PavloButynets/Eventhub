import axios from "./axios";

export const getCheckbuttonsEvents = async (
  is_my_events,
  is_joined_events,
  is_pending_events,
  is_archive_events
) => {
  const DATA_URL = "/events/checkbox-filter";

  const accessToken = localStorage.getItem("token");

  try {
    const response = await axios.post(
      DATA_URL,
      {
        user_id: null,
        is_my_events,
        is_joined_events,
        is_pending_events,
        is_archive_events,
      },
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.log("Error:", error.message);
  }
};
