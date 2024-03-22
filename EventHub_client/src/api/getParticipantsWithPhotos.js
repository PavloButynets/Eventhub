import axios from "./axios"

export const getParticipantsWithPhotos = async(eventId) =>{

    try{
        // const response = await axios.get(`events/${eventId}/participants/photos`, { headers: {"Authorization" : `Bearer ${localStorage.getItem('token')}`} })
        const response = await axios.get(`events/${eventId}/participants/photos`)
        return response.data;
    }
    catch(error){
        console.log('Error getting participants with photos data:', error);
    }
}