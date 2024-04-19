import { useState, useEffect } from "react";
import { Avatar } from "antd";
import { UserOutlined } from "@ant-design/icons";
import { getUserInfo } from "../../api/getUserInfo";
import styles from "./ProfileInfo.module.css";

const ProfileInfo = ({ onProfileClick }) => {
  const [user, setUser] = useState({
    username: "",
    email: "",
    photo_url: "",
  });

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getUserInfo();
        setUser({
          username: userData.username,
          email: userData.email,
          photo_url: userData.photo_responses[0].photo_url,
        });
      } catch (error) {
        console.error("Error fetching user data:", error);
        setUser(null);
      }
    };

    fetchUser();
  }, []);

  return (
    <div className={styles.profileInfoContainer}>
      <div className={styles.profileText}>
        {user ? (
          <>
            <span>Hi {user.username}</span>
            <span>{user.email}</span>
          </>
        ) : (
          <span>Hi Guest!</span>
        )}
      </div>
      <button className={styles.avatarButton} onClick={onProfileClick}>
        {user ? (
          <div className={styles.profileImageContainer}>
            <img
              src={user.photo_url}
              alt="profile image"
              className={styles.profileImage}
            />
          </div>
        ) : (
          <Avatar size={40} icon={<UserOutlined />} />
        )}
      </button>
    </div>
  );
};

export default ProfileInfo;
