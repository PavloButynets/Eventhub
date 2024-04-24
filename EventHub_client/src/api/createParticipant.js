import { message } from "antd";
import axios from "./axios";

export const createParticipant = async (eventId) => {
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
    const response = await authAxios.post(
      `events/${eventId}/participants/create`
    );

    return response.data;
  } catch (error) {
    console.error(error);
  }
};
