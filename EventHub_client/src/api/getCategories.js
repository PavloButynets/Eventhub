import axios from "./axios";

const CATEGORIES_URL = 'categories'

export const getCategories = async() =>{
    try{
        const response = await axios.get(CATEGORIES_URL);
        return response.data;
    }
    catch(error){
        console.log('Error getting categories', error);
    }
}