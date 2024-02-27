import { useContext } from "react";
import AuthContext from "../../context/authProvider";
import { Link } from 'react-router-dom';
import IntroMap from "./Map/Map";
import  useAuth  from "../../hooks/useAuth";
import { Button } from "antd";

const Home = () => {
  const { auth, setAuth } = useAuth();

  return (
    <div>
      {auth.token ? ( 
        <div>
          <h1>Welcome to Home Page</h1>
          <Button onClick={() => {
            localStorage.removeItem("token");
            setAuth({}); 
            }}>Click</Button>
          <IntroMap />
          
        </div>
      ) : (
        <h1>Please <Link to="/LogIn">log</Link> in or <Link to="/register">sign</Link> in to access this page</h1>
      )}
    </div>
  );
}

export default Home;
