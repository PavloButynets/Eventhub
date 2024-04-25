import { useState, useEffect, useContext } from "react";
import AuthContext from "../context/authProvider";
import useAuth from "./useAuth";

const useLogin = () => {
  const [authenticated, setAuthenticated] = useState(
    !!localStorage.getItem("token")
  );

  useEffect(() => {
    setAuthenticated(!!localStorage.getItem("token"));
  }, [localStorage.getItem("token")]);

  return authenticated;
};

export default useLogin;
