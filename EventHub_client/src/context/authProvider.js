import React, { createContext, useState, useEffect } from "react";
import { refreshToken } from "../jwt/refreshToken";
import axios from "../api/axios";
import { message } from "antd";

const LOGIN_URL = "/authentication/login";
const REGISTER_URL = "/authentication/register";
const LOGOUT_URL = "/authentication/logout";

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

    localStorage.setItem("token", accessToken);
    localStorage.setItem("refreshToken", refToken);
    localStorage.setItem("expDate", expiryDate);

    //setAuth({ refToken: refToken, expDate: expiryDate });

    // console.log(accessToken);
    // console.log(refToken);
    // console.log(expiryDate);
    // console.log(auth.refToken);
    // console.log(auth.expDate);

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

      message.error("You are loged out");
      setAuth({});

      return response.data;
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    const refresh = async (token) => {
      try {
        await refreshToken(token);
      } catch (error) {
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("token");
        localStorage.removeItem("expDate");
        message.error("You are loged out");
        setAuth({});
      }
    };

    //const { token, expDate } = auth;

    const accessToken = localStorage.getItem("token");
    const token = localStorage.getItem("refreshToken");

    //const token = auth.refToken;

    const expDate = new Date(localStorage.getItem("expDate"));
    const intervalTime = expDate.getTime() - Date.now() - 20000;

    //const expDate = new Date(auth.expDate);
    // const expiry = new Date(expDate);
    // const intervalTime = expiry.getTime() - Date.now() - 20000;

    if (accessToken) {
      const interval = setInterval(async () => {
        await refresh(token);
      }, intervalTime);

      return () => clearInterval(interval);
    }
  }, [auth]);

  return (
    <AuthContext.Provider
      value={{ auth, setAuth, login, confirmEmail, logout, register }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
