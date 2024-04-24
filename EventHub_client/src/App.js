import SignUp from './pages/SignUp/SignUp';
import LogIn from './pages/LogIn/LogIn';
import {Home} from "./pages/Home/Home";
import ChangePassword from './pages/Home/ChangePassword/ChangePassword';
import styles from "./App.css"
import './App.css';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
} from "react-router-dom";
import UserProfile from './pages/Home/UserProfile/UserProfile';
import EditUserProfile from './pages/Home/EditUserProfile/EditUserProfile';


function App() {
  return (
      <Router>
        <div>
          <Routes>
            <Route path="/" element={<Home />}>
              <Route path="event/:ownerId/:eventId" />
              <Route path="/profile/change-password" element={<ChangePassword/>}/>
              <Route path="/profile/:username" element={<UserProfile/>}/>
              <Route path="/profile/edit" element={<EditUserProfile/>}/>
            </Route>
            <Route path="/register" element={<SignUp/>}/>
            <Route path="/login" element={<LogIn/>}/> 
          </Routes>
        </div>
      </Router>
      
    );
  }

export default App
