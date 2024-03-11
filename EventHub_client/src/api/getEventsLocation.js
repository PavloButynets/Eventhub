import axios from "./axios"

const DATA_URL =  '/eventData'

export const getEventsData = async() =>{

    try{
        const response = await axios(DATA_URL)
        return response.data;
    }
    catch(error){
        console.log('Error getting places data', error);
    }
}