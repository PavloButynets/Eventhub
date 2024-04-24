import axios from "./axios";

export const sendDataWithoutPhotos = async (eventData, owner_id) => {
  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  try {
    const response = await authAxios.post(
      `/users/${owner_id}/events`,
      eventData
    );
    return response.data;
  } catch (error) {
    console.error("Error sending data without photos to server:", error);

    throw error;
  }
};

function isFormDataEmpty(formData) {
  const entries = formData.entries();
  return entries.next().done;
}
const appendFormData = (formDataArray) => {
  const mergedFormData = new FormData();
  formDataArray.forEach((formData) => {
    if (!formData) return;
    for (const [key, value] of formData.entries()) {
      mergedFormData.append("files", value);
    }
  });

  return mergedFormData;
};
export const sendPhotosToServer = async (formData, event_id) => {
  const mergedPhotos = appendFormData(formData);
  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  try {
    if (isFormDataEmpty(mergedPhotos)) return;
    const response = await authAxios.post(
      `/events/${event_id}/photos/upload`,
      mergedPhotos
    );
    return response.data;
  } catch (error) {
    console.error("Error uploading photos to server:", error);

    throw error;
  }
};
