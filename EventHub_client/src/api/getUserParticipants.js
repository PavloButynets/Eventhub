import axios from "./axios"

export const getUserParticipants = async(eventId) =>{

    try{
        // const response = await axios.get(`events/${eventId}/participants/photos`, { headers: {"Authorization" : `Bearer ${localStorage.getItem('token')}`} })
        const response = await axios.get(`events/${eventId}/participants/users`)
        return response.data;
    }
    catch(error){
        console.log('Error getting participants with photos data:', error);
    }
}