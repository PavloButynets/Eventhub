// ProfileInfo.jsx
import React from 'react';
import { Avatar } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import styles from './ProfileInfo.module.css';

const ProfileInfo = ({ nickname, email, onProfileClick }) => {
  return (
    <div className={styles.profileInfoContainer}>
      <div className={styles.profileText}>
        <span>Hi {nickname}</span>
        <span>{email}</span>
      </div>
      <button className={styles.avatarButton} onClick={onProfileClick}>
        <Avatar size={40} icon={<UserOutlined />} />
      </button>
    </div>
  );
};

export default ProfileInfo;
