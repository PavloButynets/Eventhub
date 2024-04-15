import axios from "./axios";

export const getParticipantByUserId = async (userId, eventId) => {
  try {
    const response = await axios.get(
      `events/${eventId}/participants/user/${userId}`
    );
    return response.data;
  } catch (error) {
    console.log("Error getting participant by user id:", error);
  }
};
