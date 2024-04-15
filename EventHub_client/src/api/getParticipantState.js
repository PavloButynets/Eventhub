import axios from "./axios";

export const getParticipantState = async (userId, eventId) => {
  try {
    const response = await axios.get(
      `events/${eventId}/participants/user_state/${userId}`
    );
    return response.data;
  } catch (error) {
    console.log("Error getting participant state:", error);
  }
};
