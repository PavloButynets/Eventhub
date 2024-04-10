// ProfileInfo.jsx
import React, { useEffect, useState } from "react";
import { Avatar } from "antd";
import { UserOutlined } from "@ant-design/icons";
import styles from "./ProfileInfo.module.css";
import { getUserById } from "../../api/getUserById";

const ProfileInfo = ({ userId, onProfileClick }) => {
  const [user, setUser] = useState(null);
  useEffect(() => {
    userId && getUserById(userId).then((data) => setUser(data));
  }, []);
  return (
    user && (
      <div className={styles.profileInfoContainer}>
        <div className={styles.profileText}>
          <span>Hi {user.username}</span>
          <span>{user.email}</span>
        </div>
        <button className={styles.avatarButton} onClick={onProfileClick}>
          <Avatar size={40} icon={<UserOutlined />} />
        </button>
      </div>
    )
  );
};

export default ProfileInfo;
