import React, { useState } from 'react'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import Login from './components/login.component'
import SignUp from './components/signup.component'
import Home from './components/home/home.component'

// TODO: Replace constants for HOME, SIGNIN and SIGNUP
function App() {

  const [selectedComponent, setSelectedComponent] = useState('LOGIN');

  const assignComponent = (component) => {
    setSelectedComponent(component);
  }

  const onSuccessFullSignIn = () => {
    assignComponent('HOME')
  }

  const getComponent = () => {
    switch(selectedComponent) {
      case 'LOGIN' : return <Login  onSuccessFullSignIn={onSuccessFullSignIn}/>
      case 'SIGNUP' : return <SignUp onSuccessFullSignIn={onSuccessFullSignIn}/>
      case 'HOME': return <Home/>
    }
  }

  // TODO: Move to separate component signinsignupwrapper
  const getLoginSignUpWrapper = () => {
    return (<><nav className="navbar navbar-expand-lg navbar-light fixed-top">
      <div className="container">
        <li className="nav-item">
          <a class="nav-link" href="#" onClick={() => assignComponent('LOGIN')}>Crowdfunding</a>
        </li>
        <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <a class="nav-link" href="#" onClick={() => assignComponent('LOGIN')}>Login</a>
            </li>
            <li className="nav-item">
              <a class="nav-link" href="#" onClick={() => assignComponent('SIGNUP')}>Sign up</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
      <div className="auth-wrapper">
        <div className="auth-inner">
          {getComponent()}
        </div>
      </div></>)
  }
  return (
      <div className="App">
        {
         (selectedComponent === 'LOGIN' || selectedComponent === 'SIGNUP') && getLoginSignUpWrapper()
        }
        {getComponent()}
       
        
      </div>
  )
}
export default App