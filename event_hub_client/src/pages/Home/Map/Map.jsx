import React, {useState, useRef, useCallback } from 'react';
import { GoogleMap } from '@react-google-maps/api';
import styles from  './Map.module.css'
import {light} from "./Theme"


const MAP_API_KEY =  process.env.REACT_APP_GOOGLE_MAPS_API_KEY
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
  clicableIcons:  false,
  scrollwheel: true,
  disableDoubleClickZoom: true,
  styles: light ,
  
}

const Map = ({center})=> {

  

  const mapRef = useRef(undefined)

  const onLoad = useCallback(function callback(map) {
    mapRef.current = map;
  }, [])

  const onUnmount = useCallback(function callback(map) {
    mapRef.current=map;
  }, [])

  return (
    <div className= {styles.mapcontainer}>
    <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={10}
        onLoad={onLoad}
        onUnmount={onUnmount}
        options={defaultOption}
      >
        
        { /* Child components, such as markers, info windows, etc. */ }
        <></>
      </GoogleMap>
    </div>
  );
}

export {Map};