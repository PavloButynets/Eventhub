import React, { useState } from "react";
import styles from "./UserImages.module.css";
import { SlArrowLeft, SlArrowRight } from "react-icons/sl";

const UserImages = ({ images }) => {
  const [imageIndex, setImageIndex] = useState(0);

  const showPrev = () => {
    setImageIndex((prevIndex) =>
      prevIndex === 0 ? images.length - 1 : prevIndex - 1
    );
  };

  const showNext = () => {
    setImageIndex((prevIndex) =>
      prevIndex === images.length - 1 ? 0 : prevIndex + 1
    );
  };

  return (
    <div className={styles.UserImages}>
      <div className={styles.ImageContainer}>
        <img
          className={styles.Image}
          src={images[imageIndex].photo_url}
          alt={images[imageIndex].photo_name}
        />
        {images.length > 1 && (
          <div className={styles.NavigationButtons}>
            <button className={styles.PrevButton} onClick={showPrev}>
              <SlArrowLeft />
            </button>
            <button className={styles.NextButton} onClick={showNext}>
              <SlArrowRight />
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default UserImages;
