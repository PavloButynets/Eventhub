import axios from "./axios";

export const addParticipant = async (userId, eventId, participantId) => {
  try {
    const response = await axios({
      method: "put",
      url: `http://localhost:9090/events/${eventId}/participants/${participantId}`,
      data: {
        event_id: eventId,
        user_id: userId,
      },
    });

    return response.data;
  } catch (error) {
    console.log("Error while adding participant: ", error);
  }
};
