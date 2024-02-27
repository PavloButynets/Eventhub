import AuthContext from "../context/authProvider";
import { useContext, useEffect } from "react";
const useAuth = () => {
    const { auth, setAuth } = useContext(AuthContext);
    
    // Оновлення стану автентифікації при зміні токена в локальному сховищі
    useEffect(() => {
        const savedToken = localStorage.getItem('token');
        if (savedToken !== auth.token) {
            setAuth({ token: savedToken });
        }
    }, [auth.token, setAuth]);

    return { auth, setAuth };
  };
  
  export default useAuth;
