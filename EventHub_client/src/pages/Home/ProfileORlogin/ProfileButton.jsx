// MenuButton.jsx
import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Dropdown, Menu } from "antd";
import useAuth from "../../../hooks/useAuth";
import { UserOutlined, LogoutOutlined } from "@ant-design/icons";
import ProfileInfo from "../../../components/ProfileInfo/ProfileInfo";
import styles from "./Buttons.module.css";

const MenuButton = ({ user }) => {
  const { setAuth } = useAuth();
  const [isOpen, setIsOpen] = useState(false);

  const handleMenuClick = (e) => {
    if (e.key === "profile") {
      console.log("Profile clicked");
    } else if (e.key === "logout") {
      localStorage.removeItem("token");
      setAuth({});
    }
    setIsOpen(false);
  };

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  return (
    <Dropdown
      overlay={
        <Menu className={styles.customMenu} onClick={handleMenuClick}>
          <Link to={`/profile/${user.id}/account`}>
            <Menu.Item icon={<UserOutlined />} key="profile">
              Profile
            </Menu.Item>
          </Link>
          <Menu.Item icon={<LogoutOutlined />} key="logout">
            Log out
          </Menu.Item>
        </Menu>
      }
      trigger={["click"]}
      visible={isOpen}
      onVisibleChange={toggleMenu}
    >
      <ProfileInfo userId={user.id} onProfileClick={toggleMenu} />
    </Dropdown>
  );
};

export default MenuButton;
