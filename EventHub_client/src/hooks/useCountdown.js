import { useState, useEffect } from "react";

const useCountdown = (seconds, onFinish) => {
  const [secondsLeft, setSecondsLeft] = useState(seconds);

  useEffect(() => {
    if (secondsLeft <= 0) {
      onFinish();
      return;
    }
    const timeout = setTimeout(() => {
      setSecondsLeft(secondsLeft - 1);
    }, 1000);

    return () => clearTimeout(timeout);
  }, [secondsLeft]);

  return secondsLeft;
};

export default useCountdown;
