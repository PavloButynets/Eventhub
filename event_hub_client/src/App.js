import SignUp from './pages/SignUp/SignUp';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
} from "react-router-dom";

import './App.css';
import SignIn from "./pages/LogIn/LogIn";

function App() {
  return (
      <Router>
        <div>
          <Routes>
            <Route path="/" element={<SignUp/>}/>
          </Routes>
        </div>
      </Router>
      
    );
}

export default App;
