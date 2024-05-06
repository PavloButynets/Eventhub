import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Input, Select, DatePicker, Checkbox, message } from "antd";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import CancelButton from "../../../components/Buttons/CancelButton/CancelButton";
import ApplyChangesButton from "../../../components/Buttons/ApplyChangesButton/ApplyChangesButton";
import { CameraOutlined, DeleteOutlined } from "@ant-design/icons";
import { sendDataWithoutPhotos } from "../../../api/updateUserInfo";
import { deleteUserPhotos } from "../../../api/updateUserInfo";
import { sendPhotosToServer } from "../../../api/updateUserInfo";
import { getUserInfo } from "../../../api/getUserInfo";
import { PlacesAutocomplete } from "../../../components/PlaceAutocomplete/PlaceAutocomplete";
import dayjs from "dayjs";
import styles from "./EditUserProfile.module.css";
import ProcessingEffect from "../../../components/ProcessingEffect/ProcessingEffect";

const EditUserProfile = () => {
  const { TextArea } = Input;
  const { Option } = Select;
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [submitChanges, setSubmitChanges] = useState(false);

  const [user, setUser] = useState(null);
  const [cancelAddress, setCancelAddress] = useState(false);

  const [userExistingPhotos, setUserExistingPhotos] = useState(null);
  const [photos, setPhotos] = useState(new Array(4).fill(null));
  const [uploadedPhotos, setUploadedPhotos] = useState(new Array(4).fill(null));
  const [toDeletePhotos, setToDeletePhotos] = useState([]);
  const [photoIndex, setPhotoIndex] = useState(0);

  const fetchUser = async () => {
    try {
      const response = await getUserInfo();

      setUser({
        first_name: response.first_name,
        last_name: response.last_name,
        username: response.username,
        email: response.email,
        description: response.description,
        city: response.city,
        birth_date: response.birth_date ? dayjs(response.birth_date) : null,
        gender: response.gender,
        show_email: response.show_email,
      });

      const userPhotos = response.photo_responses.filter(
        (photo) => photo.photo_name !== "userDefaultImage"
      );
      setUserExistingPhotos(userPhotos);
      setPhotos(
        userPhotos
          .map((photo) => photo.photo_url)
          .concat(new Array(4 - userPhotos.length).fill(null))
      );
      setPhotoIndex(userPhotos.length);

      setLoading(true);
    } catch (error) {
      navigate("/login");
    }
  };
  useEffect(() => {
    fetchUser();
  }, []);

  const handlePhotoUpload = (event) => {
    const file = event.target.files[0];
    photos[photoIndex] = URL.createObjectURL(file);

    const newFormData = new FormData();
    newFormData.append("files", file);
    uploadedPhotos[photoIndex] = newFormData;

    setPhotos(photos);
    setUploadedPhotos(uploadedPhotos);
    setPhotoIndex(photoIndex + 1);
  };

  const handlePhotoDelete = (index) => {
    for (let photo of userExistingPhotos) {
      if (photo.photo_url === photos[index]) {
        setToDeletePhotos([...toDeletePhotos, photo.id]);
      }
    }
    photos.splice(index, 1);
    uploadedPhotos.splice(index, 1);

    photos.push(null);
    uploadedPhotos.push(null);

    setPhotoIndex(photoIndex - 1);
    setPhotos(photos);
    setUploadedPhotos(uploadedPhotos);
  };

  const updateUserInfo = (event) => {
    const { name, value } = event.target;
    setUser({
      ...user,
      [name]: value,
    });
  };

  const handleCityUpdate = (value) => {
    setUser({
      ...user,
      city: value,
    });
  };

  const applyChanges = async (event) => {
    event.preventDefault();

    try {
      setSubmitChanges(true);
      await sendDataWithoutPhotos({
        ...user,
        birth_date: user.birth_date && user.birth_date.add(1, "day"),
      });
      await deleteUserPhotos(toDeletePhotos);
      await sendPhotosToServer(uploadedPhotos);

      handleClose();
      message.success("Successfully updated");
    } catch (error) {
      message.error(error.response.data);
    } finally {
      setSubmitChanges(false);
    }
  };

  const handleCancel = async (event) => {
    event.preventDefault();
    await fetchUser();
    setCancelAddress(!cancelAddress);
    setUploadedPhotos(new Array(4).fill(null));
    setToDeletePhotos([]);
  };

  const handleClose = (e) => {
    // e.preventDefault();
    navigate("/");
  };
  return (
    <div className={styles.OuterContainer}>
      {!loading ? (
        <ProcessingEffect />
      ) : (
        <>
          {submitChanges && <ProcessingEffect />}
          <form className={styles.InnerContainer}>
            <div className={styles.Header}>
              <p className={styles.Heading}>Edit account information</p>
              <CloseWindowButton onClick={handleClose} />
            </div>
            <div className={styles.Main}>
              <div className={styles.Photos}>
                {photos.map((photo, index) =>
                  index === photoIndex ? (
                    <div className={styles.Photo} key={index}>
                      <label className={styles.AddPhotoLabel}>
                        <input
                          type="file"
                          accept="image/*"
                          onChange={(event) => handlePhotoUpload(event)}
                          style={{ display: "none" }}
                        />
                        Add Photo
                      </label>
                    </div>
                  ) : (
                    <div className={styles.Photo} key={index}>
                      {photo ? (
                        <>
                          <div
                            className={styles.Delete}
                            onClick={() => handlePhotoDelete(index)}
                          >
                            <div className={styles.DeleteButton}>
                              <DeleteOutlined />
                            </div>
                          </div>
                          <img
                            className={styles.Image}
                            src={photo}
                            alt="image"
                          />
                        </>
                      ) : (
                        <CameraOutlined />
                      )}
                    </div>
                  )
                )}
              </div>
              <div className={styles.MainInfo}>
                <div className={styles.InputContainer}>
                  <p className={styles.Caption}>Name</p>
                  <Input
                    name="first_name"
                    placeholder="Name"
                    className={styles.Param}
                    value={user.first_name}
                    onChange={updateUserInfo}
                  />
                </div>
                <div className={styles.InputContainer}>
                  <p className={styles.Caption}>Surname</p>
                  <Input
                    name="last_name"
                    placeholder="Surname"
                    className={styles.Param}
                    value={user.last_name}
                    onChange={updateUserInfo}
                  />
                </div>
                <div className={styles.InputContainer}>
                  <p className={styles.Caption}>Username</p>
                  <Input
                    name="username"
                    placeholder="Nickname"
                    className={styles.Param}
                    value={user.username}
                    onChange={updateUserInfo}
                  />
                </div>
                <div className={styles.InputContainer}>
                  <p className={styles.Caption}>Address</p>
                  <PlacesAutocomplete
                    onSelectLocation={handleCityUpdate}
                    initialValue={user.city}
                    cancelChanges={cancelAddress}
                  />
                </div>
                <div className={styles.InputContainer}>
                  <p className={styles.Caption}>Gender</p>
                  <Select
                    name="gender"
                    placeholder="Gender"
                    className={styles.Select}
                    value={user.gender}
                    onChange={(value) => setUser({ ...user, gender: value })}
                  >
                    <Option value="MALE">Male</Option>
                    <Option value="FEMALE">Female</Option>
                    <Option value="OTHER">Other</Option>
                  </Select>
                </div>
                <div className={styles.InputContainer}>
                  <p className={styles.Caption}>Birthday</p>
                  <DatePicker
                    className={styles.Param}
                    format={"YYYY-MM-DD"}
                    name="birth_date"
                    value={user.birth_date}
                    onChange={(value) =>
                      setUser({ ...user, birth_date: value })
                    }
                  />
                </div>
                <div className={styles.InputContainer}>
                  <Checkbox
                    checked={user.show_email}
                    name="show_email"
                    onChange={updateUserInfo}
                    value={!user.show_email}
                  >
                    <p className={styles.Caption}>Show e-mail for others</p>
                  </Checkbox>
                </div>
              </div>
            </div>
            <div className={styles.Description}>
              <p className={styles.Caption}>About</p>
              <TextArea
                autoSize={{ minRows: 5, maxRows: 5 }}
                name="description"
                placeholder="Enter description..."
                className={styles.TextArea}
                value={user.description}
                onChange={updateUserInfo}
              />
            </div>
            <div className={styles.Buttons}>
              <CancelButton onclick={handleCancel} />
              <ApplyChangesButton onClick={applyChanges} />
            </div>
          </form>
        </>
      )}
    </div>
  );
};

export default EditUserProfile;
