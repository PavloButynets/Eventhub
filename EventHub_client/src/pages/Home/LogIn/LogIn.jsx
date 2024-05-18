import { useState } from "react";
import SignIn from "./SignIn/SignIn";
import ForgotPassword from "./ForgotPassword/ForgotPassword";
import styles from "./LogIn.module.css";

const Login = () => {
  const [login, setLogin] = useState(true);

  return (
    <div className={styles.OuterContainer}>
      {login ? (
        <SignIn forgotPassword={() => setLogin(false)} />
      ) : (
        <ForgotPassword logIn={()=>setLogin(true)}/>
      )}
    </div>
  );
};

export default Login;
