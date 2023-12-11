import React, { Component } from "react";
import Profile from "../profile/profile.component";
import Projects from "../projects/projects.component";
import Transactions from '../transactions/transactions.component'
import { COMPONENTS, ACCOUNT_TYPE } from '../../helpers/constants';


import './home.scss'

export default class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedComponent: COMPONENTS.PROJECTS,
    };
  }

  assignComponent = (component) => {
    this.setState({
      selectedComponent: component,
    });
  };

  getComponent = () => {
    const {user} = this.props;
    switch (this.state.selectedComponent) {
      case COMPONENTS.PROFILE:
        return <Profile user={user}/>;
      case COMPONENTS.PROJECTS:
        return <Projects user={user}/>;
      case COMPONENTS.TRANSACTIONS: 
        return <Transactions user={user}/>
      default:
        return <></>;
    }
  };

  render() {
    const { user, onLogout } = this.props;
    return (
      <>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
          <div class="container-fluid">
            <a class="navbar-brand" href="#">
              Crowdfunding
            </a>
            <button
              class="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                  <a
                    class="nav-link"
                    href="#"
                    onClick={() => this.assignComponent(COMPONENTS.PROFILE)}
                  >
                    Profile
                  </a>
                </li>
                <li class="nav-item">
                  <a
                    class="nav-link active"
                    aria-current="page"
                    href="#"
                    onClick={() => this.assignComponent(COMPONENTS.PROJECTS)}
                  >
                    Projects
                  </a>
                </li>
                {
                  user.role === ACCOUNT_TYPE.DONOR && 
                  <li class="nav-item">
                  <a
                    class="nav-link active"
                    aria-current="page"
                    href="#"
                    onClick={() => this.assignComponent(COMPONENTS.TRANSACTIONS)}
                  >
                    Transactions
                  </a>
                </li>
                }
              </ul>

              <h6 class='text-muted welcome-text'>Welcome, {`${user.firstName} ${user.lastName}`}</h6>
              <button class="btn btn-outline-secondary logout-button" type="button" onClick={onLogout}>Logout</button>
            </div>
          </div>
        </nav>
        {this.getComponent()}
      </>
    );
  }
}
