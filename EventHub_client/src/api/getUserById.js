import axios from "./axios"

export const getUserById = async(userId) =>{

    try{
        // const response = await axios.get(`events/${eventId}/participants/photos`, { headers: {"Authorization" : `Bearer ${localStorage.getItem('token')}`} })
        const response = await axios.get(`users/${userId}`)
        return response.data;
    }
    catch(error){
        console.log('Error getting user with Id: ', error);
    }
}