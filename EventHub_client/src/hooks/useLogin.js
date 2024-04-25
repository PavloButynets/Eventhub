import { useState, useEffect, useContext } from "react";
import AuthContext from "../context/authProvider";

const useLogin = () => {
  const [authenticated, setAuthenticated] = useState(!!localStorage.getItem("token"));

  useEffect(() => {
    setAuthenticated(!authenticated);
  }, [localStorage.getItem("token")]);

  return authenticated;
};

export default useLogin;
