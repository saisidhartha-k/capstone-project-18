import "./navbar.scss";
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";
import LightModeOutlinedIcon from '@mui/icons-material/LightModeOutlined';

// import { DarkModeContext } from "../../context/darkModeContext";
import { useContext, useState } from "react";

const Navbar = () => {
  // const { dispatch } = useContext(DarkModeContext);
  const [isDarkMode, setDarkMode] = useState(false);

  const toggleDarkMode = () => {
    setDarkMode(!isDarkMode);
  };


  return (
    <div className={`navbar ${isDarkMode ? 'dark-mode' : ''}`}>
    <div className="wrapper">
      <div className="items">
        <div className="item" onClick={toggleDarkMode}>
          {isDarkMode ? (
            <DarkModeOutlinedIcon className="icon" />
          ) : (
            <LightModeOutlinedIcon className="icon" />
          )}
        </div>

          <div className="item">
            <img
               src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdsHr0_k8PrNgg8SYhwVlV4ONxwliFxDLSDCC6TTqe&s"
              alt=""
              className="avatar"
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
