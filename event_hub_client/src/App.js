import SignUp from './pages/SignUp/SignUp';
import LogIn from './pages/LogIn/LogIn';
import Home from "./pages/Home/Home";
import './App.css';

import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
} from "react-router-dom";



function App() {
  return (
      <Router>
        <div>
          <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/register" element={<SignUp/>}/>
            <Route path="/login" element={<LogIn/>}/> 
          </Routes>
        </div>
      </Router>
      
    );
}

export default App;
