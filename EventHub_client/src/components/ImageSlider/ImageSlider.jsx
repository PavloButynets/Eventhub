import { useState } from 'react';
import styles from './ImageSlider.module.css'

import { SlArrowLeft } from "react-icons/sl";
import { SlArrowRight } from "react-icons/sl";

const ImageSlider = ({images}) => {

    const [imageIndex, setImageIndex] = useState(0);

    const showPrev = () => {
        setImageIndex(index => {
            if (index === 0) {
                return images.length-1;
            }
            return --index;
        })
    }

    const showNext = () => {
        setImageIndex(index => {
            if (index === images.length-1) {
                return 0;
            }
            return ++index;
        })
    }

    return ( 
        <div className={styles['image-slider']}>
            {images.map(image => (
                <img className={styles['image-slider-img']} key={image.id} src={image.photo_url} alt="Event img" />
            ))}
            {images.length > 1 && <div className={styles['arrow-container']}>
                    <button className={styles['arrow']} onClick={showPrev}> <SlArrowLeft className={styles['arrow-icon']} /> </button>
                    <button className={styles['arrow']} onClick={showNext}> <SlArrowRight className={styles['arrow-icon']} /> </button>
            </div>}
        </div>
     );
}
 
export default ImageSlider;