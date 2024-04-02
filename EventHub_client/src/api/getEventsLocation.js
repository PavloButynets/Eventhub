// import axios from "./axios"

// //const DATA_URL =  '/eventData'
// const DATA_URL = "/users/events"

// const options = {
//     headers: {
        
//       'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuaWNrQG1haWwuY29tIiwiaWF0IjoxNzExNzI5MDg3LCJleHAiOjE3MTE3MzA1Mjd9.czACIhhAyWbDAS8RslNors1s9bFWt2BD3FpA8SXtcdM`,
//       'Access-Control-Allow-Credentials':true,
//       'Access-Control-Allow-Origin': '*'
//     }
//   };

// export const getEventsData = async() =>{

//     try{
//         const response = await axios.get(DATA_URL, options);
//     }
//     catch(error){
//         console.log('Error getting places data', error);
//     }
// }

import axios from "./axios"

//const DATA_URL =  '/eventData'
const DATA_URL = "http://localhost:9090"

// const options = {
//     headers: {
        
//       'Authorization': `token`,
//       'Access-Control-Allow-Credentials':true,
//       'Access-Control-Allow-Origin': '*'
//     }
//   };

//const accessToken = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuaWNrQG1haWwuY29tIiwiaWF0IjoxNzExOTgwNzQ1LCJleHAiOjE3MTE5ODIxODV9.7ihJGWttXYkyxD5zPGLhdoOXjTNO9lIyMvjzw2pRsQM'

const accessToken = localStorage.getItem('token')

console.log(accessToken)

const authAxios = axios.create({
  // baseURL: DATA_URL,
  headers: {
    Authorization: `Bearer ${accessToken}`,
    'Access-Control-Allow-Origin': '*',
    "Access-Control-Allow-Headers": "content-type",
    "Access-Control-Allow-Credentials": "true"
  }
})

export const getEventsData = async() =>{
    try{
        const response = await authAxios.get(`/users/events`)
        return response.data;
    }
    catch(error){
        console.log('Error getting events data', error);
    }
}