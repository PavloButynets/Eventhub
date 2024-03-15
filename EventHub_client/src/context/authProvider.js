import React, { createContext, useState, useEffect } from "react";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState({});

    useEffect(() => {
        const savedToken = localStorage.getItem('token');
        if (savedToken) {
            setAuth({ token: savedToken });
        }
        const tokenChangeHandler = (event) => {
            if (event.key === 'token') {
                setAuth(prevAuth => ({ ...prevAuth, token: event.newValue }));
            }
        };
        window.addEventListener('storage', tokenChangeHandler);
        return () => {
            window.removeEventListener('storage', tokenChangeHandler);
        };
    }, []); 

    
    return (
        <AuthContext.Provider value={{ auth, setAuth }}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthContext;

