import axios from "./axios"

export const getEventById = async(userId, eventId) => {

    try{
        // const response = await axios.get(`events/${eventId}/participants/photos`, { headers: {"Authorization" : `Bearer ${localStorage.getItem('token')}`} })
        const response = await axios.get(`/users/${userId}/events/${eventId}`);
        return response.data;
    }
    catch(error){
        console.log('Error getting event with Id: ', error);
    }
}