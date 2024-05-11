import React from 'react';
import useCountdown from "../../hooks/useCountdown";
import styles from "./Countdowncircle.module.css";

const CountdownCircle = ({ seconds, onFinish }) => {
    const secondsLeft = useCountdown(seconds, onFinish);
  
    const percentageElapsed = ((seconds - secondsLeft) / seconds) * 100;
    const strokeDashoffset = (126 * (100 - percentageElapsed)) / 100;
  
    return (
      <div style={{ position: 'relative', width: '100px', height: '100px' }}>
        <svg width="100" height="100">
          <circle
            className={styles.backgroundCircle}
            cx="50"
            cy="50"
            r="20"
          />
          <circle
            className={styles.animatedBorder}
            cx="50"
            cy="50"
            r="20"
            strokeDashoffset={strokeDashoffset}
          />
        </svg>
        <div className={styles.countdownText}>
          {secondsLeft}
        </div>
      </div>
    );
};

export default CountdownCircle;
