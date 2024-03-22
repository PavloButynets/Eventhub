import axios from "./axios"

export const getEventsData = async(eventId) =>{

    try{
        const response = await axios(`events/${eventId}/participants/photos`)
        return response.data;
    }
    catch(error){
        console.log('Error getting participants with photos data:', error);
    }
}