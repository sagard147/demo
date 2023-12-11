import React, { useState, useEffect } from "react";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js";
import "./App.css";
import Login from "./components/login/login.component";
import SignUp from "./components/signup/signup.component";
import Home from "./components/home/home.component";
import { CROWDFUND_SESSION_NAME, COMPONENTS } from './helpers/constants';
import { getSession } from './helpers/utils';



function App() {
  const [selectedComponent, setSelectedComponent] = useState("LOGIN");
  const [user, setUser] = useState(undefined);

  useEffect(() => {
      // Check for an existing user session in sessionStorage
      const storedUser = getSession(CROWDFUND_SESSION_NAME);
      if (storedUser) {
        storedUser.role = storedUser.accountType;
        setUser(storedUser);
        assignComponent(COMPONENTS.HOME);
      }
    }, []);


  const assignComponent = (component) => {
    setSelectedComponent(component);
  };

  const onSuccessFullSignIn = (userObj) => {
    userObj.role = userObj.accountType;
    setUser(userObj);
    assignComponent(COMPONENTS.HOME);
  };

  const logoutUser = () => {
    sessionStorage.removeItem(CROWDFUND_SESSION_NAME);
    assignComponent(COMPONENTS.LOGIN)
  }

  const getComponent = () => {
    switch (selectedComponent) {
      case COMPONENTS.LOGIN:
        return <Login onSuccessFullSignIn={onSuccessFullSignIn} />;
      case COMPONENTS.SIGNUP:
        return <SignUp onSuccessFullSignIn={onSuccessFullSignIn} />;
      case COMPONENTS.HOME:
        return <Home user={user} onLogout={logoutUser}/>;
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
                // onClick={() => assignComponent(COMPONENTS.LOGIN)}
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
                    onClick={() => assignComponent(COMPONENTS.LOGIN)}
                  >
                    Login
                  </a>
                </li>
                <li className="nav-item">
                  <a
                    class="nav-link"
                    href="#"
                    onClick={() => assignComponent(COMPONENTS.SIGNUP)}
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
      {(selectedComponent === COMPONENTS.LOGIN || selectedComponent === COMPONENTS.SIGNUP) &&
        getLoginSignUpWrapper()}
      {!(selectedComponent === COMPONENTS.LOGIN || selectedComponent === COMPONENTS.SIGNUP) &&
        getComponent()}
    </div>
  );
}
export default App;