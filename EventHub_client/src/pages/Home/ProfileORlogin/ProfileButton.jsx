// MenuButton.jsx
import React, { useState } from "react";
import { Dropdown, Menu } from "antd";
import useAuth from "../../../hooks/useAuth";
import { UserOutlined, LogoutOutlined } from "@ant-design/icons";
import ProfileInfo from "../../../components/ProfileInfo/ProfileInfo";
import styles from "./Buttons.module.css";
import { Link, useNavigate } from "react-router-dom";

const MenuButton = () => {
  const { setAuth } = useAuth();
  const [isOpen, setIsOpen] = useState(false);

  const navigate = useNavigate();

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
          <Link style={{ all: "unset" }}>
            <Menu.Item icon={<UserOutlined />} key="profile">
              Profile
            </Menu.Item>
          </Link>
          <Menu.Item
            onClick={() => navigate("/")}
            icon={<LogoutOutlined />}
            key="logout"
          >
            Log out
          </Menu.Item>
        </Menu>
      }
      trigger={["click"]}
      visible={isOpen}
      onVisibleChange={toggleMenu}
    >
      <ProfileInfo onProfileClick={toggleMenu} />
    </Dropdown>
  );
};

export default MenuButton;
