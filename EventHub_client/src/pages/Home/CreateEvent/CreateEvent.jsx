import React, { useState, useEffect } from 'react';
import {useSearchParams } from 'react-router-dom';
import styles from './CreateEvent.module.css';
import { CameraOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import CloseWindowButton from '../../../components/Buttons/CloseWindowButton/CloseWindowButton';
import { Input, Select, DatePicker, Checkbox, AutoComplete, message } from 'antd';
import { getCategories } from '../../../api/getCategories';
import { MinusCircleOutlined } from '@ant-design/icons';
import { jwtDecode } from 'jwt-decode'
import GetLocationByCoordinates from "../../../api/getLocationByCoordinates"

import usePlacesAutocomplete, {
  getGeocode,
  getLatLng,
} from "use-places-autocomplete";
import { sendDataWithoutPhotos } from '../../../api/sendEventData';
import { sendPhotosToServer } from '../../../api/sendEventData';
const { TextArea } = Input;
const { Option } = Select;
const MAP_API_KEY = process.env.REACT_APP_GOOGLE_MAPS_API_KEY

const FullSizePhotoModal = ({ photoUrl, onClose }) => {
  return (
    <div className={styles.fullSizePhotoModal}>
      <div className={styles.modalContent}>
        <img src={photoUrl} alt="Full Size Photo" />
        <button className={styles.closeButton} onClick={onClose}>Close</button>
      </div>
    </div>
  );
};


const PlacesAutocomplete = ({ onSelectLocation }) => {

  const [searchParams] = useSearchParams();

  useEffect(() => {
    const lat = searchParams.get('latitude');
    const lng = searchParams.get('longitude');
    const fetchDefaultLocation = async () => {
      try {
        const location = await GetLocationByCoordinates(lat, lng);
        if (location) {
          const defaultAddress = [location.city, location.street, location.houseNumber].join(', ');
          setValue(defaultAddress);
          onSelectLocation({ address: defaultAddress, lat, lng });

        }
      } catch (error) {
        console.error('Error getting location:', error);
      }
    };
    if (lat && lng) {
      fetchDefaultLocation();
    }
  }, []);

  const {
    ready,
    value,
    suggestions: { status, data },
    setValue,
    clearSuggestions,
  } = usePlacesAutocomplete({
    requestOptions: {
      /* Define search scope here */
    },
    debounce: 300,
  });

  const handleInput = (value) => {
    setValue(value);
  };

  const handleSelect = (value) => {
    setValue(value)

    clearSuggestions();

    // Get latitude and longitude via utility functions
    getGeocode({ address: value }).then((results) => {
      const { lat, lng } = getLatLng(results[0]);
      console.log("📍 Coordinates: ", { lat, lng });
      onSelectLocation({ address: value, lat, lng });
    });
  };

  const options = data.map((suggestion) => ({
    value: suggestion.description,
    label: (
      <div>
        <strong>{suggestion.structured_formatting.main_text}</strong>
        <small>{suggestion.structured_formatting.secondary_text}</small>
      </div>
    ),
  }));

  return (
    <AutoComplete
      options={options}
      onSelect={handleSelect}
      onSearch={handleInput}
      value={value}
      disabled={!ready}
      placeholder="Where are you going?"
      className={styles.Param}
      defaultActiveFirstOption={false}
    />
  );
};




const CreateEvent = () => {
  const [photos, setPhotos] = useState(new Array(6).fill(null));
  const [addedPhotos, setAddedPhotos] = useState(0);
  const [hoveredPhotoIndex, setHoveredPhotoIndex] = useState(-1);
  const [fullSizePhotoIndex, setFullSizePhotoIndex] = useState(-1);
  const [searchParams, setsearchParams] = useSearchParams();
  const [isCreateEvent, setIsCreateEvent] = useState(false);
  const [categories, setCategories] = useState([]);

  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [latitude, setLatitude] = useState(0);
  const [longitude, setLongitude] = useState(0);
  const [location, setLocation] = useState('');
  const [withOwner, setWithOwner] = useState(true);
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [participants, setParticipants] = useState('');
  const [dateRange, setDateRange] = useState(null);

  const [formData, setFormData] = useState(new FormData());
  useEffect(() => {
    // Отримання категорії з серверу під час завантаження компонента
    const fetchCategories = async () => {
      try {
        const categoriesData = await getCategories();
        setCategories(categoriesData);
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };

    fetchCategories();
  }, []);
  useEffect(() => {
    const createEventParam = searchParams.get('create_event');
    setIsCreateEvent(createEventParam === 'true');
  }, [searchParams]);

  const resetCreate = () =>{
    // to implements
  }

  const handlePhotoUpload = (index, event) => {
    const file = event.target.files[0];
    const newPhotos = [...photos];
    newPhotos[index] = URL.createObjectURL(file);

    //
    formData.append('files', file);
    setFormData(formData);
    //

    setPhotos(newPhotos);
    setAddedPhotos(addedPhotos + 1);
  };

  const handlePhotoDelete = (index) => {
    const newPhotos = [...photos];
    newPhotos[index] = null;
    setPhotos(newPhotos);
    setAddedPhotos(addedPhotos - 1);

    // Зміщення наступних фото назад
    for (let i = index; i < photos.length - 1; i++) {
      if (newPhotos[i] === null && newPhotos[i + 1] !== null) {
        [newPhotos[i], newPhotos[i + 1]] = [newPhotos[i + 1], newPhotos[i]];
      }
    }
  };

  const handleFullSizePhoto = (index) => {
    setFullSizePhotoIndex(index);
  };

  const handleCloseButton = () => {
    setTitle('')
    setDescription('')
    setLatitude(0)
    setLongitude(0)
    setLocation('')
    setWithOwner(false)
    setSelectedCategories([])
    setParticipants('')
    setDateRange(null)
    setPhotos(new Array(6).fill(null));
    setAddedPhotos(0)
    setFormData(new FormData());
    setsearchParams({});

  }


  const handleCategoryChange = (values) => {

    setSelectedCategories(values);
  };

  const handleLocationChange = (value) => {
    const address = value.address;
    const latitude = value.lat;
    const longitude = value.lng;

    setLocation(address);
    setLatitude(latitude);
    setLongitude(longitude);
  };

  const handleDescriptionChange = (e) => {
    setDescription(e.target.value);
  };

  const handleCheckboxChange = (e) => {
    setWithOwner(!withOwner);
  };

  const handleDateChange = (dates) => {
    setDateRange(dates);
  };


  const formatDate = (dateString) => {
    const date = new Date(dateString);

    const offset = date.getTimezoneOffset();
    date.setHours(date.getHours() - offset / 60);
    return date.toISOString();
  };

  const validateFields = () => {
  
    if (title.length < 5 || title.length > 20) {
      message.error('Event name must be between 5 and 20 characters');
      return false;
    }
  
    // Перевірка поля "Кількість учасників"
    if (!participants || isNaN(participants) || participants < 2 || participants > 20000) {
      message.error('Number of participants must be a number between 2 and 20,000');
      return false;
    }
  
    
    if (!dateRange) {
      message.error('Start and End date cannot be empty');
      return false;
    }
 
    if (!description || description.trim() === '') {
      message.error('Event description cannot be empty');
      return false;
    } else if (description.length > 255) {
      message.error('Event description cannot exceed 255 characters');
      return false;
    }
    if (selectedCategories.length === 0) {
      message.error('At least one category must be selected');
      return false;
    }

    if (!location || location.trim() === '') {
      message.error('Event location cannot be empty');
      return false;
    }
  

    return true;
  }
  const handleSubmit = async () => {
    try {
      if (!validateFields()) {
        return; 
      }
      const startAt = formatDate(dateRange[0]);
      const expireAt = formatDate(dateRange[1]);
      const authToken = localStorage.getItem('token');
      console.log("Категорії", selectedCategories)


      const user = jwtDecode(authToken);
      const user_id = user.id;
      const eventData = {
        title: title,
        max_participants: participants,
        start_at: startAt,
        expire_at: expireAt,
        description: description,
        latitude: latitude,
        longitude: longitude,
        location: location,
        with_owner: withOwner,
        category_requests:  selectedCategories.map(category => ({name: category})),
        current_count: 0,
        owner_id: user_id,
    };
    console.log("Event Data:", eventData);
    const textDataResponse = await sendDataWithoutPhotos(eventData,user_id);
    
    const eventId = textDataResponse.id;
    console.log("Event Id from server", eventId);
    console.log(typeof(photos[0]), photos[0])
    const photoDataResponse = await sendPhotosToServer(formData,eventId);
    // console.log("Photo response data: ",photoDataResponse)
    
    message.success('Event successfully created');
    console.log("Form data: ", formData);
    
    
    setsearchParams({});

      message.success('Event successfully created');
      setsearchParams({});
      setTitle('')
      setDescription('')
      setLatitude(0)
      setLongitude(0)
      setLocation('')
      setWithOwner(false)
      setSelectedCategories([])
      setParticipants('')
      setPhotos(new Array(6).fill(null));
      setAddedPhotos(0)
      setDateRange(null)
      setFormData(new FormData());

    } catch (error) {
      console.error('Error submitting event:', error);
      message.error('Failed to create event. Please try again later.');
    }
  };


  return (
    <>
      {isCreateEvent &&
        <div className={styles.backdrop}>
          <div className={styles.mainContainer}>
            <div className={styles.createEventHeader}>
              <h2>Create Event</h2>
              <div className={styles.CloseButton}>
                <CloseWindowButton onClick={handleCloseButton} />
              </div>
            </div>
            <div className={styles.photoContainer}>
              {photos.map((photo, index) => (
                <div
                  key={index}
                  className={styles.photo}
                  onMouseEnter={() => setHoveredPhotoIndex(index)}
                  onMouseLeave={() => setHoveredPhotoIndex(-1)}
                >
                  {index === 0 && (
                    <div className={styles.miniContainer}>
                      <span className={styles.mainPhotoText}>Main</span>
                    </div>
                  )}
                  {photo ? (
                    <>
                      <img src={photo} alt={`Photo ${index}`} />
                      {hoveredPhotoIndex === index && (
                        <div className={styles.photoActions}>
                          <div className={styles.actionIcon}>
                            <DeleteOutlined onClick={() => handlePhotoDelete(index)} style={{ fontSize: '24px', color: '#FF0000', cursor: 'pointer' }} />
                          </div>
                          <div className={styles.actionIcon}>
                            <EyeOutlined onClick={() => handleFullSizePhoto(index)} style={{ fontSize: '24px', color: '#FFFFF', cursor: 'pointer' }} />
                          </div>
                        </div>
                      )}
                    </>
                  ) : (
                    <>
                      {addedPhotos === index && (
                        <label className={styles.addPhotoLabel}>
                          <input type="file" accept="image/*" onChange={(event) => handlePhotoUpload(index, event)} style={{ display: 'none' }} />
                          Add Photo
                        </label>
                      )}
                      {addedPhotos !== index && (
                        <div className={styles.cameraIcon}>
                          <CameraOutlined style={{ fontSize: '24px', color: '#AAAAAA', cursor: 'pointer' }} />
                        </div>
                      )}
                    </>
                  )}
                </div>
              ))}
            </div>
            <div className={styles.ParamsContainer}>
              {/* Перший рядок */}
              <div className={styles.row}>
                <div className={styles.ParamContainer}>
                  <div className={styles.ParamLabel}>Name</div>
                  <Input
                    placeholder="Name"
                    className={styles.Param}
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                  />
                </div>
                <div className={styles.ParamContainer}>
                  <div className={styles.ParamLabel}>Categories</div>
                  <Select
                    className={styles.Param}
                    placeholder="Categories"
                    mode="multiple"
                    maxTagCount={3}
                    maxTagPlaceholder={<MinusCircleOutlined />}
                    value={selectedCategories}
                    onChange={handleCategoryChange}
                  >
                    {categories.map(category => (
                      <Option key={category.id} value={category.name}>{category.name}</Option>
                    ))}
                  </Select>
                </div>
              </div>
              {/* Другий рядок */}
              <div className={styles.row}>
                <div className={styles.ParamContainer}>
                  <div className={styles.ParamLabel}>Location</div>
                  <PlacesAutocomplete
                    onSelectLocation={handleLocationChange}
                  />
                </div>
                <div className={styles.ParamContainer}>
                  <div className={styles.ParamLabel}>Participants</div>
                  <Input
                    placeholder="Participants"
                    className={styles.Param}
                    value={participants}
                    onChange={(e) => setParticipants(e.target.value)}
                  />
                </div>
              </div>
              {/* Третій рядок */}
              <div className={styles.row}>
                <div className={styles.ParamContainer} >
                  <div className={styles.ParamLabel}>Start date and time - End date and time</div>
                  <DatePicker.RangePicker
                    showTime={{ format: 'HH:mm' }}
                    format="YYYY-MM-DD HH:mm"
                    placeholder={['Start date and time', 'End date and time']}
                    style={{ width: '100%', height: "4vh", zIndex: 999 }}
                    onChange={handleDateChange}
                  />
                </div>
              </div>
            </div>
            <div className={styles.DescriptionContainer}>
              <div className={styles.ParamLabel}>Description</div>
              <TextArea
                autoSize={{ minRows: 2, maxRows: 5 }}

                placeholder="Enter description..."
                value={description}
                onChange={handleDescriptionChange}
              />
            </div>
            <div className={styles.ParticipationContainer}>
              <Checkbox className={styles.Checkbox}
                checked={withOwner}
                onChange={handleCheckboxChange}
              >I take part in this event</Checkbox>
            </div>
            <div className={styles.CreateButtonContainer}>
              <button className={styles.CreateButton} onClick={handleSubmit} >Create Event</button>
            </div>
          </div>
        </div>}
    </>
  );
};

export default CreateEvent;
