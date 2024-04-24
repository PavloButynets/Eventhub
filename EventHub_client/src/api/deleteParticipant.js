import { message } from "antd";
import axios from "./axios";

export const deleteParticipant = async (participantId, eventId) => {
  try {
    const accessToken = localStorage.getItem("token");
    const authAxios = axios.create({
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers": "content-type",
        "Access-Control-Allow-Credentials": "true",
      },
    });
    const response = await authAxios.delete(
      `events/${eventId}/participants/${participantId}`
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
};
