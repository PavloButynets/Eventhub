import axios from "./axios";

export const editDataWithoutPhotos = async (eventData, owner_id, event_id) => {
  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });

  const response = await authAxios.put(
    `/users/${owner_id}/events/${event_id}`,
    eventData
  );
  return response.data;
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

export const editEventPhotos = async (formData, event_id) => {
  const mergedPhotos = appendFormData(formData);
  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  if (isFormDataEmpty(mergedPhotos)) return;
  const response = await authAxios.post(
    `/events/${event_id}/photos/upload`,
    mergedPhotos
  );
  console.log(response.data);
  return response.data;
};

export const deleteEvent = async (owner_id, event_id) => {
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
    `/users/${owner_id}/events/${event_id}`
  );
  return response.data;
};

export const deleteEventPhotos = async (event_id, photos) => {
  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });

  for (let photo_id of photos) {
    await authAxios.delete(`/events/${event_id}/photos/${photo_id}`);
  }
};
