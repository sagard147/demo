import React, { useState, useEffect } from "react";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js";
import "./App.css";
import Login from "./components/login.component";
import SignUp from "./components/signup.component";
import Home from "./components/home/home.component";

// TODO: Replace constants for HOME, SIGNIN and SIGNUP

const getSession = (key) => {
  const sessionData = sessionStorage.getItem(key);
  if (sessionData) {
    const parsedData = JSON.parse(sessionData);
    const currentTime = new Date().getTime();

    // Check if the session has not expired
    if (parsedData.expirationTime > currentTime) {
      return parsedData.data;
    } else {
      // Remove the expired session
      sessionStorage.removeItem(key);
    }
  }
  return null;
};


function App() {
  const [selectedComponent, setSelectedComponent] = useState("LOGIN");
  const [user, setUser] = useState(undefined);

  useEffect(() => {
      // Check for an existing user session in sessionStorage
      const storedUser = getSession("crowdFundUser");
      if (false && storedUser) {
        storedUser.role = storedUser.accountType;
        setUser(storedUser);
        assignComponent("HOME");
      }
    }, []);


  const assignComponent = (component) => {
    setSelectedComponent(component);
  };

  const onSuccessFullSignIn = (userObj) => {
    userObj.role = userObj.accountType;
    setUser(userObj);
    assignComponent("HOME");
  };

  const getComponent = () => {
    switch (selectedComponent) {
      case "LOGIN":
        return <Login onSuccessFullSignIn={onSuccessFullSignIn} />;
      case "SIGNUP":
        return <SignUp onSuccessFullSignIn={onSuccessFullSignIn} />;
      case "HOME":
        return <Home user={user} />;
      default: return <></>;
    }
  };

  // TODO: Move to separate component signinsignupwrapper
  const getLoginSignUpWrapper = () => {
    return (
      <>
        <nav className="navbar navbar-expand-lg navbar-light fixed-top">
          <div className="container">
            <li className="nav-item">
              <a
                class="nav-link"
                href="#"
                onClick={() => assignComponent("LOGIN")}
              >
                Crowdfunding
              </a>
            </li>
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <a
                    class="nav-link"
                    href="#"
                    onClick={() => assignComponent("LOGIN")}
                  >
                    Login
                  </a>
                </li>
                <li className="nav-item">
                  <a
                    class="nav-link"
                    href="#"
                    onClick={() => assignComponent("SIGNUP")}
                  >
                    Sign up
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </nav>
        <div className="auth-wrapper">
          <div className="auth-inner">{getComponent()}</div>
        </div>
      </>
    );
  };
  return (
    <div className="App">
      {(selectedComponent === "LOGIN" || selectedComponent === "SIGNUP") &&
        getLoginSignUpWrapper()}
      {!(selectedComponent === "LOGIN" || selectedComponent === "SIGNUP") &&
        getComponent()}
    </div>
  );
}
export default App;