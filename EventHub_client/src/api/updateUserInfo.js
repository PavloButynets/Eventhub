import axios from "./axios";

export const sendDataWithoutPhotos = async (userData) => {
  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  const response = await authAxios.put("/users", userData);
  return response.data;
};

const isFormDataEmpty = (formData) => {
  const entries = formData.entries();
  return entries.next().done;
};

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

export const sendPhotosToServer = async (formData) => {
  const mergedFormData = appendFormData(formData);

  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  if (isFormDataEmpty(mergedFormData)) return;
  const response = await authAxios.post("/users/photos/upload", mergedFormData);
  return response.data;
};

export const deleteUserPhotos = async (photos) => {
  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });

  for (let photo of photos) {
    await authAxios.delete(`/users/photos/${photo}`);
  }
};
