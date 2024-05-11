import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { message } from "antd";
import CountdownCircle from "../../../../components/CountdownCircle/CountdownCircle";
import { resedVerificationEmail } from "../../../../api/resendVerificationEmail";
import PrimaryButton from "../../../../components/Buttons/PrimaryButton/PrimaryButton"
import ProcessingEffect from "../../../../components/ProcessingEffect/ProcessingEffect";
import styles from "./EmailVerification.module.css";
import image from "../../../../images/EmailImage1.png";

const EmailVerification = ({ email }) => {
  const navigate = useNavigate();
  const [processing, setProcessing] = useState(false);

  const resendEmail = async (resendTo) => {
    try {
      setProcessing(true);
      await resedVerificationEmail(resendTo);
    } catch (error) {
      message.error("Failed to resend verification email!");
    } finally {
      setProcessing(false);
    }
  };

  return processing ? (
    <ProcessingEffect />
  ) : (
    <div className={styles.OuterContainer}>
      <div className={styles.InnerContainer}>
        <div className={styles.EmailImage}>
          <img className={styles.Image} src={image} alt="Email Image 1" />
        </div>
        <p className={styles.Heading}>Email Confirmation</p>
        <p className={styles.MainInfo}>
          We have sent email to <span className={styles.Email}>{email}</span> to
          confirm the validity of your email address. After receiving the email,
          follow the link provided to complete your registration. The window
          will be automatically closed when you will confirm the email.
        </p>
        <p className={styles.SecondText}>
          If you haven’t got any email just press the button to resend it!
        </p>
        <div className={styles.Bottom}>
          <PrimaryButton children={"Resend Email"} onClick={() => resendEmail(email)} className={styles.Button}/>
          <CountdownCircle seconds={60} onFinish={() => navigate("/")} />
        </div>
      </div>
    </div>
  );
};

export default EmailVerification;
