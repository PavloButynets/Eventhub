import React, { useState, useEffect, useRef } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import usePlacesAutocomplete, {
  getGeocode,
  getLatLng,
} from "use-places-autocomplete";
import dayjs from "dayjs";
import getIdFromToken from "../../../jwt/getIdFromToken";
import styles from "./EditEvent.module.css";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import { Input, Select, DatePicker, AutoComplete, message } from "antd";
import {
  CameraOutlined,
  DeleteOutlined,
  EyeOutlined,
  MinusCircleOutlined,
} from "@ant-design/icons";

import { getCategories } from "../../../api/getCategories";
import { getFullEventById } from "../../../api/getFullEventById";
import {
  editEventPhotos,
  editDataWithoutPhotos,
  deleteEvent,
  deleteEventPhotos,
} from "../../../api/editEventData";
import ProcessingEffect from "../../../components/ProcessingEffect/ProcessingEffect";

const { TextArea } = Input;
const { Option } = Select;

const FullSizePhotoModal = ({ photoUrl, handleClosePhoto }) => {
  const modalRef = useRef(null);

  const handleModalClick = (event) => {
    if (modalRef.current && !modalRef.current.contains(event.target)) {
      handleClosePhoto();
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === "Escape") {
      handleClosePhoto();
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleModalClick);
    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("mousedown", handleModalClick);
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);

  return (
    <div className={styles.fullSizePhotoModal} ref={modalRef}>
      <img src={photoUrl} alt="Full Size Photo" />
    </div>
  );
};

const PlacesAutocomplete = ({ onSelectLocation, defaultAddress }) => {
  useEffect(() => {
    setValue(defaultAddress || "");
  }, [defaultAddress]);
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
    setValue(value);

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

const EditEvent = () => {
  const [eventExistingPhotos, setEventExistingPhotos] = useState(null);
  const [photos, setPhotos] = useState(new Array(6).fill(null));
  const [photosToDelete, setPhotosToDelete] = useState([]);
  const [addedPhotos, setAddedPhotos] = useState(0);
  const [formData, setFormData] = useState(new FormData());
  const [hoveredPhotoIndex, setHoveredPhotoIndex] = useState(-1);
  const [fullSizePhotoIndex, setFullSizePhotoIndex] = useState(-1);

  const [categories, setCategories] = useState([]);

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [latitude, setLatitude] = useState(0);
  const [longitude, setLongitude] = useState(0);
  const [location, setLocation] = useState("");
  const [withOwner, setWithOwner] = useState(true);
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [participants, setParticipants] = useState("");
  const [dateRange, setDateRange] = useState(null);
  const [ownerId, setOwnerId] = useState(null);

  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [submitChanges, setSubmitChanges] = useState(false);

  const userId = getIdFromToken();

  const eventId = searchParams.get("eventId");

  const [startDate, setStartDate] = useState(dayjs());
  const [expireDate, setExpireDate] = useState(dayjs());

  useEffect(() => {
    const fetchData = async () => {
      try {
        const eventData = await getFullEventById(eventId);

        setTitle(eventData.title || "");
        setDescription(eventData.description || "");
        setLatitude(eventData.latitude || 0);
        setLongitude(eventData.longitude || 0);
        setLocation(eventData.location || "");
        setWithOwner(eventData.withOwner || true);
        setOwnerId(eventData.owner_id);
        setSelectedCategories(
          eventData.category_responses?.map((category) => category.name) || []
        );
        setParticipants(eventData.max_participants || "");

        const startAt = new Date(eventData.start_at);
        const expiresAt = new Date(eventData.expire_at);
        setDateRange([startAt, expiresAt]);
        setStartDate(dayjs(startAt));
        setExpireDate(dayjs(expiresAt));

        const userPhotos = eventData.photo_responses.filter(
          (photo) => photo.photo_name !== "eventDefaultImage"
        );
        setEventExistingPhotos(userPhotos);
        setPhotos(
          userPhotos
            .map((photo) => photo.photo_url)
            .concat(new Array(6 - userPhotos.length).fill(null))
        );
        setAddedPhotos(userPhotos.length);
      } catch (error) {
        console.error("Error fetching event data:", error);
      }
    };

    fetchData();
  }, [eventId]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const categoriesData = await getCategories();
        setCategories(categoriesData);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };
    fetchCategories();
  }, []);

  const handlePhotoUpload = (index, event) => {
    const file = event.target.files[0];
    photos[index] = URL.createObjectURL(file);
    const newFormDataPhotos = [...formData];
    const data = new FormData();

    data.append("files", file);
    newFormDataPhotos[index] = data;

    setFormData(newFormDataPhotos);
    setPhotos(photos);
    setAddedPhotos(addedPhotos + 1);
  };

  const handlePhotoDelete = (index) => {
    const newPhotos = [...photos];
    const newFormDataPhotos = [...formData];

    console.log(photos[index]);
    for (let photo of eventExistingPhotos) {
      if (photo.photo_url === photos[index]) {
        setPhotosToDelete([...photosToDelete, photo.id]);
      }
    }

    newPhotos[index] = null;
    newFormDataPhotos[index] = null;
    setPhotos(newPhotos);
    setFormData(newFormDataPhotos);
    setAddedPhotos(addedPhotos - 1);

    // Зміщення наступних фото назад
    for (let i = index; i < photos.length - 1; i++) {
      if (newPhotos[i] === null && newPhotos[i + 1] !== null) {
        [newPhotos[i], newPhotos[i + 1]] = [newPhotos[i + 1], newPhotos[i]];
        [newFormDataPhotos[i], newFormDataPhotos[i + 1]] = [
          newFormDataPhotos[i + 1],
          newFormDataPhotos[i],
        ];
      }
    }
  };
  const handleDateRangeChange = (dates) => {
    if (dates && dates.length === 2) {
      const [newStartDate, newExpireDate] = dates;
      setStartDate(newStartDate);
      setExpireDate(newExpireDate);
    }
    setDateRange(dates);
  };
  const clearEventData = () => {
    setTitle("");
    setDescription("");
    setLatitude(0);
    setLongitude(0);
    setLocation("");
    setWithOwner(false);
    setSelectedCategories([]);
    setParticipants("");
    setPhotos(new Array(6).fill(null));
    setAddedPhotos(0);
    setDateRange(null);
    setFormData(new FormData());
    navigate(`/event/${eventId}`);
  };

  const handleLocationChange = (value) => {
    setLocation(value.address);
    setLatitude(value.lat);
    setLongitude(value.lng);
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);

    const offset = date.getTimezoneOffset();
    date.setHours(date.getHours() - offset / 60);
    return date.toISOString();
  };

  const validateFields = () => {
    if (title.length < 5 || title.length > 20) {
      message.error("Event name must be between 5 and 20 characters");
      return false;
    }

    if (
      !participants ||
      isNaN(participants) ||
      participants < 2 ||
      participants > 20000
    ) {
      message.error(
        "Number of participants must be a number between 2 and 20,000"
      );
      return false;
    }

    if (!dateRange) {
      message.error("Start and End date cannot be empty");
      return false;
    }

    if (!description || description.trim() === "") {
      message.error("Event description cannot be empty");
      return false;
    } else if (description.length > 255) {
      message.error("Event description cannot exceed 255 characters");
      return false;
    }

    if (selectedCategories.length === 0) {
      message.error("At least one category must be selected");
      return false;
    }

    if (!location || location.trim() === "") {
      message.error("Event location cannot be empty");
      return false;
    }

    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateFields()) {
      return;
    }
    setSubmitChanges(true);
    try {
      const startAt = formatDate(dateRange[0]);
      const expireAt = formatDate(dateRange[1]);

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
        category_requests: selectedCategories.map((category) => ({
          name: category,
        })),
        owner_id: ownerId,
      };

      await editDataWithoutPhotos(eventData, eventId);
      await deleteEventPhotos(eventId, photosToDelete);
      await editEventPhotos(formData, eventId);

      navigate(`/event/${eventId}`);
      message.success("Event was successfully edited");
      clearEventData();
    } catch (error) {
      if (error.response.status === 403) {
        message.error(error.response.data);
        navigate("/");
      } else {
        console.error("Error submitting event:", error);
        message.error(error.response.data);
        navigate("/");
      }
    } finally {
      setSubmitChanges(false);
    }
  };
  const handleDelete = async () => {
    try {
      // Видалення всіх існуючих фотографій
      if (eventExistingPhotos) {
        const photoIds = eventExistingPhotos.map((photo) => photo.id);
        await deleteEventPhotos(eventId, photoIds);
      }
      await deleteEvent(eventId);
      clearEventData();
      navigate("/");
      message.success("Event deleted successfully!");
    } catch (error) {
      message.error("Error deleting event");
      console.error("Error deleting event:", error);
    }
  };
  const handleKeyDown = (event) => {

    if (event.key === 'Escape') {
      event.preventDefault(); 
      clearEventData();
    }
  };

  useEffect(() => {
    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);

  return eventId && userId ? (
    <div className={styles.backdrop}>
      {submitChanges && <ProcessingEffect />}

      <div className={styles.wrapper}>
        <div className={styles.mainContainer}>
          <div className={styles.editEventHeader}>
            <h2>Edit Event</h2>
            <div className={styles.CloseButton}>
              <CloseWindowButton onClick={() => clearEventData()} />
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
                          <DeleteOutlined
                            onClick={() => handlePhotoDelete(index)}
                            style={{
                              fontSize: "24px",
                              color: "#FF0000",
                              cursor: "pointer",
                            }}
                          />
                        </div>
                        <div className={styles.actionIcon}>
                          <EyeOutlined
                            onClick={() => setFullSizePhotoIndex(index)}
                            handleClosePhoto={() => setFullSizePhotoIndex(-1)}
                            style={{
                              fontSize: "24px",
                              color: "#FFFFF",
                              cursor: "pointer",
                            }}
                          />
                        </div>
                      </div>
                    )}
                  </>
                ) : (
                  <>
                    {addedPhotos === index && (
                      <label className={styles.addPhotoLabel}>
                        <input
                          type="file"
                          accept="image/*"
                          onChange={(event) => handlePhotoUpload(index, event)}
                          style={{ display: "none" }}
                        />
                        Add Photo
                      </label>
                    )}
                    {addedPhotos !== index && (
                      <div className={styles.cameraIcon}>
                        <CameraOutlined
                          style={{
                            fontSize: "24px",
                            color: "#AAAAAA",
                            cursor: "pointer",
                          }}
                        />
                      </div>
                    )}
                  </>
                )}
              </div>
            ))}
          </div>
          <div className={styles.ParamsContainer}>
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
                  maxTagCount={2}
                  maxTagPlaceholder={<MinusCircleOutlined />}
                  value={selectedCategories}
                  onChange={(values) => setSelectedCategories(values)}
                >
                  {categories.map((category) => (
                    <Option key={category.id} value={category.name}>
                      {category.name}
                    </Option>
                  ))}
                </Select>
              </div>
            </div>
            <div className={styles.row}>
              <div className={styles.ParamContainer}>
                <div className={styles.ParamLabel}>Location</div>
                <PlacesAutocomplete
                  defaultAddress={location}
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
            <div className={styles.row}>
              <div className={styles.ParamContainer}>
                <div className={styles.ParamLabel}>
                  Start date and time - End date and time
                </div>

                <DatePicker.RangePicker
                  showTime={{ format: "HH:mm" }}
                  format="YYYY-MM-DD HH:mm"
                  placeholder={["Start date and time", "End date and time"]}
                  style={{ width: "100%", height: "4vh", zIndex: 999 }}
                  onChange={(dates) => handleDateRangeChange(dates)}
                  value={[startDate, expireDate]}
                />
              </div>
            </div>
          </div>
          <div className={styles.DescriptionContainer}>
            <div className={styles.ParamLabel}>Description</div>
            <TextArea
              autoSize={{
                minRows: 2,
                maxRows: window.innerHeight < 900 ? 2 : 5,
              }}
              placeholder="Enter description..."
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>
          <div className={styles.ButtonContainer}>
            <button className={styles.Button} onClick={handleSubmit}>
              Apply changes
            </button>
            <button className={styles.DeleteButton} onClick={handleDelete}>
              Delete
            </button>
          </div>
          {fullSizePhotoIndex !== -1 && (
            <FullSizePhotoModal
              photoUrl={photos[fullSizePhotoIndex]}
              handleClosePhoto={() => setFullSizePhotoIndex(-1)}
            />
          )}
        </div>
      </div>
    </div>
  ) : (
    navigate({ pathname: "../" })
  );
};

export default EditEvent;
