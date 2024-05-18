import React, { createContext, useState, useEffect } from "react";
import { refreshToken } from "../jwt/refreshToken";
import axios from "../api/axios";
import { message } from "antd";

import Cookies from "js-cookie";

const LOGIN_URL = "/authentication/login";
const REGISTER_URL = "/authentication/register";
const LOGOUT_URL = "/authentication/logout";
const GOOGLE_AUTH_URL = "/authentication/google";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState({});

  useEffect(() => {
    const savedToken = localStorage.getItem("token");

    if (savedToken) {
      setAuth({ token: savedToken });
    }
    const tokenChangeHandler = (event) => {
      if (event.key === "token") {
        setAuth((prevAuth) => ({ ...prevAuth, token: event.newValue }));
      }
    };
    window.addEventListener("storage", tokenChangeHandler);
    return () => {
      window.removeEventListener("storage", tokenChangeHandler);
    };
  }, []);

  const login = async (email, password) => {
    const res = await axios.post(
      LOGIN_URL,
      {
        email,
        password,
      },
      {
        headers: { "Content-Type": "application/json" },
      }
    );

    const accessToken = res?.data?.accessToken;
    const refToken = res?.data?.refreshToken;
    const expiryDate = res?.data?.expiryDate;

    //Cookies.set("ref", refToken);
    //Cookies.set("exp", expiryDate);

    localStorage.setItem("token", accessToken);
    localStorage.setItem("refreshToken", refToken);
    localStorage.setItem("expDate", expiryDate);

    axios.defaults.headers.common[
      "Authorization"
    ] = `Bearer ${res.data["token"]}`;
  };

  const register = async (userData) => {
    await axios.post(REGISTER_URL, userData, {
      headers: { "Content-Type": "application/json" },
    });
  };

  const confirmEmail = async (emailToken) => {
    const res = await axios.get(
      `authentication/confirm-account?token=${emailToken}`,
      emailToken,
      {
        headers: { "Content-Type": "application/json" },
      }
    );
    const accessToken = res?.data?.accessToken;
    const refToken = res?.data?.refreshToken;
    const expiryDate = res?.data?.expiryDate;

    localStorage.setItem("token", accessToken);
    localStorage.setItem("refreshToken", refToken);
    localStorage.setItem("expDate", expiryDate);
  };

  const logout = async () => {
    const accessToken = localStorage.getItem("token");

    try {
      const response = await axios.post(
        LOGOUT_URL,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );

      localStorage.removeItem("token");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("expDate");

      message.info("You are loged out");
      setAuth({});

      return response.data;
    } catch (error) {
      console.error(error);
    }
  };

  const googleAuth = async (googleToken) => {
    try {
      const res = await axios.post(
        GOOGLE_AUTH_URL,
        { google_token: googleToken },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const accessToken = res?.data?.accessToken;
      const refToken = res?.data?.refreshToken;
      const expiryDate = res?.data?.expiryDate;

      if (accessToken && refToken && expiryDate) {
        localStorage.setItem("token", accessToken);
        localStorage.setItem("refreshToken", refToken);
        localStorage.setItem("expDate", expiryDate);
      }

      return res;
    } catch (err) {
      if (!err.response) {
        // Помилка з'єднання з сервером
        message.error("No server response");
      } else {
        message.error(err.response.data);
      }
    }
  };

  useEffect(() => {
    const accessToken = localStorage.getItem("token");

    const token = localStorage.getItem("refreshToken");
    //const token = Cookies.get("ref");

    const expDate = new Date(localStorage.getItem("expDate"));
    //const expDate = new Date(Cookies.get("exp"));

    const intervalTime = expDate.getTime() - Date.now() - 20000;

    if (accessToken) {
      const interval = setInterval(async () => {
        try {
          console.log("refresh");
          await refreshToken(token);
        } catch (error) {
          localStorage.removeItem("refreshToken");
          localStorage.removeItem("token");
          localStorage.removeItem("expDate");
          message.error("You are loged out");
          setAuth({});
        }
      }, intervalTime);

      return () => clearInterval(interval);
    }
  }, [auth]);

  return (
    <AuthContext.Provider
      value={{
        auth,
        setAuth,
        login,
        confirmEmail,
        logout,
        register,
        googleAuth,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
