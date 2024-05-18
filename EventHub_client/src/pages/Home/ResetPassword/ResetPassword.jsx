import { useState, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Input, message } from "antd";
import { FaUnlock } from "react-icons/fa";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import PrimaryButton from "../../../components/Buttons/PrimaryButton/PrimaryButton";
import ProcessingEffect from "../../../components/ProcessingEffect/ProcessingEffect";
import AuthContext from "../../../context/authProvider";
import styles from "./ResetPassword.module.css";

const ResetPassword = () => {
  const navigate = useNavigate();
  const { token } = useParams();
  const { confirmResetPassword } = useContext(AuthContext);

  const [newPassword, setNewPassword] = useState("");
  const [confirmNewPassword, setConfirmNewPassword] = useState("");

  const [isProcessed, setIsProcessed] = useState(false);

  const handleSubmit = async () => {
    if(newPassword === "" || confirmNewPassword === ""){
      message.error("Password cannot be empty!");
      return;
    }
    if (newPassword !== confirmNewPassword) {
      message.error("Passwords do not match");
      return;
    }

    if (!token) {
      message.error("Token is missing");
      return;
    }
    try {
      setIsProcessed(true);
      await confirmResetPassword({
        token: token,
        new_password: newPassword,
      });
      message.success("Login successful!");
      navigate("/");
      window.location.reload();
    } catch (error) {
      if (error.response) {
        message.error(error.response.data);
      } else {
        message.error("Error!");
      }
    } finally {
      setIsProcessed(false);
    }
  };
  return (
    <>
      {isProcessed && <ProcessingEffect />}
      <div className={styles.OuterContainer}>
        <div className={styles.InnerContainer}>
          <div className={styles.Buttons}>
            <p className={styles.Caption}>Reset password</p>
            <CloseWindowButton
              onClick={() => {
                navigate("/");
              }}
            />
          </div>
          <FaUnlock className={styles.Lock} />
          <div className={styles.InstructionsContainer}>
            <p className={styles.SecondaryCaption}>
              Follow these instructions:
            </p>
            <div className={styles.Instructions}>
              1. Enter your new password in the "New password" field.
              <br />
              2. Confirm your new password by entering it again in the "Confirm
              new password" field.
              <br />
              3. Click on the "Reset Password" button to complete the process.
            </div>
          </div>

          <div className={styles.PasswordsContainer}>
            <div className={styles.Password}>
              <p className={styles.PasswordCaption}>New password</p>
              <Input.Password
                className={styles.PasswordField}
                type="password"
                placeholder="Enter new password"
                name="new_password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
              />
            </div>
            <div className={styles.Password}>
              <p className={styles.PasswordCaption}>Confirm new password</p>
              <Input.Password
                className={styles.PasswordField}
                type="password"
                placeholder="Confirm new password"
                name="confirmNewPassword"
                value={confirmNewPassword}
                onChange={(e) => setConfirmNewPassword(e.target.value)}
              />
            </div>
          </div>
          <PrimaryButton
            children={"Reset"}
            className={styles.ResetButton}
            onClick={handleSubmit}
          />
        </div>
      </div>
    </>
  );
};

export default ResetPassword;
