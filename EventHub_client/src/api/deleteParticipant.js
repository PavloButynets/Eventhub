import axios from "./axios";

export const deleteParticipant = async (participantId, eventId) => {
  try {
    const response = await axios.delete(
      `events/${eventId}/participants/${participantId}`
    );
    return response.data;
  } catch (error) {
    console.log("Error deleting participant:", error);
  }
};
