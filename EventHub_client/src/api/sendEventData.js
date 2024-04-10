import axios from "./axios";


export const sendDataWithoutPhotos = async (eventData, owner_id) => {
    const accessToken = localStorage.getItem('token')
    const authAxios = axios.create({
        headers: {
            Authorization: `Bearer ${accessToken}`,
            'Access-Control-Allow-Origin': '*',
            "Access-Control-Allow-Headers": "content-type",
            "Access-Control-Allow-Credentials": "true"
        }
    })
    try {
        console.log(eventData);
        const response = await authAxios.post(`/users/${owner_id}/events`, eventData);
        return response.data;
    } catch (error) {
        console.error('Error sending data without photos to server:', error);

        throw error;
    }
};

function isFormDataEmpty(formData) {
    const entries = formData.entries();
    return entries.next().done;
}

export const sendPhotosToServer = async (formData, event_id) => {
    console.log("Images: ", formData);
    const accessToken = localStorage.getItem('token')
    const authAxios = axios.create({
        headers: {
            Authorization: `Bearer ${accessToken}`,
            'Access-Control-Allow-Origin': '*',
            "Access-Control-Allow-Credentials": "true"
        }
    })
    try {
        if(isFormDataEmpty(formData)) return;
        const response = await authAxios.post(`/events/${event_id}/photos/upload`, formData);
        return response.data;

    } catch (error) {
        console.error('Error uploading photos to server:', error);

        throw error;
    }
};
