import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import useAuth from "../../../hooks/useAuth";
import { Input, message } from "antd";
import { LoadingOutlined } from "@ant-design/icons";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import CancelButton from "../../../components/Buttons/CancelButton/CancelButton";
import ApplyChangesButton from "../../../components/Buttons/ApplyChangesButton/ApplyChangesButton";
import { changePassword } from "../../../api/changePassword";
import styles from "./ChangePassword.module.css";

const ChangePassword = () => {
  const { auth, setAuth } = useAuth();
  const navigate = useNavigate();
  const [passwords, setPasswords] = useState({
    old_password: "",
    new_password: "",
    confirmNewPassword: "",
  });
  const [show, setShow] = useState(false);
  const [submitChanges, setSubmitChanges] = useState(false);

  useEffect(() => {
    if (!auth.token) {
      navigate("/login");
    }
    setShow(true);
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setPasswords({
      ...passwords,
      [name]: value,
    });
  };

  const confirmPassword = () => {
    if (passwords.new_password !== passwords.confirmNewPassword) {
      throw new Error("Failed to confirm password");
    }
  };
  const handleApply = async () => {
    try {
      setSubmitChanges(true);
      confirmPassword();
      await changePassword(passwords);
      message.success("Password successfully updated");
      handleClose();
    } catch (error) {
      if (error.response && error.response.data) {
        message.error("Password change failed: " + error.response.data); // Access response data from the error object
      } else {
        message.error("Password change failed: " + error.message); // Fallback message if response data is not available
      }
    } finally {
      setSubmitChanges(false);
    }
  };

  const handleClose = () => {
    navigate("/");
  };

  return (
    show && (
      <>
        {submitChanges &&
        <div className={styles.SubmitChanges}>
          <LoadingOutlined
            style={{ color: "white", fontSize: "72px", fontWeight: "1000" }}
          />
        </div>}
        <div className={styles.OuterContainer}>
          <div className={styles.InnerContainer}>
            <div className={styles.Buttons}>
              <p className={styles.Caption}>Change password</p>
              <CloseWindowButton onClick={handleClose} />
            </div>
            <div className={styles.PasswordsContainer}>
              <div className={styles.Password}>
                <p className={styles.PasswordCaption}>Old password</p>
                <Input.Password
                  className={styles.PasswordField}
                  type="password"
                  placeholder="Enter old password"
                  name="old_password"
                  value={passwords.old_password}
                  onChange={handleChange}
                />
              </div>
              <div className={styles.Password}>
                <p className={styles.PasswordCaption}>New password</p>
                <Input.Password
                  className={styles.PasswordField}
                  type="password"
                  placeholder="Enter new password"
                  name="new_password"
                  value={passwords.new_password}
                  onChange={handleChange}
                />
              </div>
              <div className={styles.Password}>
                <p className={styles.PasswordCaption}>Confirm new password</p>
                <Input.Password
                  className={styles.PasswordField}
                  type="password"
                  placeholder="Confirm new password"
                  name="confirmNewPassword"
                  value={passwords.confirmNewPassword}
                  onChange={handleChange}
                />
              </div>
            </div>
            <div className={styles.Buttons}>
              <CancelButton onclick={handleClose} />
              <ApplyChangesButton onClick={handleApply} />
            </div>
          </div>
        </div>
      </>
    )
  );
};

export default ChangePassword;
