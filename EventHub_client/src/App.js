import SignUp from './pages/SignUp/SignUp';
import LogIn from './pages/LogIn/LogIn';
import {Home} from "./pages/Home/Home";
import styles from "./App.css"
import './App.css';
import Profile from "./pages/Profile/Profile.jsx"
import UserInfo from "./pages/Profile/UserInfo/UserInfo.jsx"
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
            <Route path="/" element={<Home />}>
              <Route path="event/:ownerId/:eventId" />
            </Route>
            <Route path="/register" element={<SignUp/>}/>
            <Route path="/login" element={<LogIn/>}/> 
            <Route path="/profile/:userId" element={<Profile/>}>
              <Route path='/profile/:userId/account' element={<UserInfo />} />
            </Route>
          </Routes>
        </div>
      </Router>
      
    );
  }

export default App
