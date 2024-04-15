import axios from "./axios";

export const createParticipant = async (userId, eventId) => {
  try {
    const response = await axios({
      method: "post",
      url: `http://localhost:9090/events/${eventId}/participants`,
      data: {
        event_id: eventId,
        user_id: userId,
      },
    });

    return response.data;
  } catch (error) {
    console.log("Error creating participant:", error);
  }
};
