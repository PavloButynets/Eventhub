import React, { useState, useRef, useCallback, useEffect } from 'react';
import { getEventsData } from '../../../api/getEventsLocation';
import { GoogleMap, Marker,InfoWindow } from '@react-google-maps/api';
import styles from './Map.module.css'
import { useParams, useSearchParams } from 'react-router-dom';
import { getEventsDataSearch } from '../../../api/getEventsData';
import { useLocation } from 'react-router-dom';
import { EnvironmentOutlined } from '@ant-design/icons';
import { light } from "./Theme"

import { useNavigate } from 'react-router-dom';
import { getFilteredEvents } from '../../../api/getFilteredEvents';



const MAP_API_KEY = process.env.REACT_APP_GOOGLE_MAPS_API_KEY
const containerStyle = {
  width: '100%',
  height: '100%',

};

const defaultOption = {
  panControl: true,
  zoomControl: false,
  mapTypeControl: false,
  scaleControl: false,
  streetViewControl: false,
  rotateControl: false,
  fullscreenControl: false,
  clicableIcons: false,
  scrollwheel: true,
  disableDoubleClickZoom: true,
  styles: light,

}

const Map = ({ center }) => {

  const mapRef = useRef(undefined)

  const navigate = useNavigate();

  const onLoad = useCallback(function callback(map) {
    mapRef.current = map;
  }, [])

  const onUnmount = useCallback(function callback(map) {
    mapRef.current = map;
  }, [])
  
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);

  
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const searchValue = searchParams.get('search');

    const fetchData = async () => {
      if (searchValue) {
        try {
          const data = await getEventsDataSearch(searchValue);
          setEvents(data);
        } catch(error) {
          console.error('Error getting events data:', error);
        }
      } 
      else if(searchParams.get('show_filter')){
        try{
          const data = await getFilteredEvents();
          setEvents(data);
        }catch(error){
          console.error('Error getting events data:', error);
        }
      }
      else {
        getEventsData()
          .then(data => {
            setEvents(data);
          })
          .catch(error => {
            console.error('Error getting events data:', error);
          });
      }
    };

    fetchData();
  }, [searchParams]);
  const onMarkerClick = (event) => {
    setSelectedEvent(event);
    navigate(`/event/${event.owner_id}/${event.id}`);
  };

  const onMapClick = () => {
    setSelectedEvent(null);
  };
  return (
    <div className={styles.mapcontainer}>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={10}
        onLoad={onLoad}
        onUnmount={onUnmount}
        options={defaultOption}
        onClick={onMapClick}
      >

        <></>
        
        {events && events.map(event => (
          <Marker
            key={event.eventID}
            position={{ lat: Number(event.latitude), lng: Number(event.longitude) }}

            icon={{ url: '/images/pin.svg', 
            scaledSize: new window.google.maps.Size(40, 40) }}
            onClick={() => onMarkerClick(event)
            }
          />
        ))}
        
      </GoogleMap>
      
    </div>
  );
}

export { Map };