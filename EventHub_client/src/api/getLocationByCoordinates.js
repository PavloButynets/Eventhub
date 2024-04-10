import React from 'react';
import axios from 'axios';
const MAP_API_KEY = process.env.REACT_APP_GOOGLE_MAPS_API_KEY

const GetLocationByCoordinates = async (lat, lng) => {
  try {
    const response = await axios.get(`https://maps.googleapis.com/maps/api/geocode/json`, {
      params: {
        latlng: `${lat},${lng}`,
        key: MAP_API_KEY, // додайте ваш ключ API
      },
    });
    
    const { status, results } = response.data;
    console.log(results)
    if (status === 'OK') {
      const addressComponents = results[0].address_components;
      let city = '';
      let street = '';
      let houseNumber = '';

      for (let i = 0; i < results.length; i++) {
        const addressComponents = results[i].address_components;
        for (let j = 0; j < addressComponents.length; j++) {
          const component = addressComponents[j];
          if (component.types.includes('locality') && !city) {
            city = component.long_name;
          }
          if (component.types.includes('route') && !street) {
            street = component.long_name;
          }
          if (component.types.includes('street_number') && !houseNumber) {
            houseNumber = component.long_name;
          }
          if (city && street && houseNumber) {
            break;
          }
        }
        if (city && street && houseNumber) {
          break;
        }
      }

      return {
        lat,
        lng,
        city,
        street,
        houseNumber,
      };
    } else {
      console.error('Geocoding request failed:', status);
      return null;
    }
  } catch (error) {
    console.error('Error performing geocoding request:', error);
    return null;
  }
};

export default GetLocationByCoordinates