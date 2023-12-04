import React, { Component } from 'react'
import Profile from '../profile/profile.component'
import Projects from '../projects/projects.component'


export default class Home extends Component {
    

    constructor(props) {
        super(props);
        this.state = {
            selectedComponent : 'PROFILE'
        }
    }

    assignComponent = (component) => {
        this.setState({
            selectedComponent: component
        })
      }

      getComponent = () => {
        switch(this.state.selectedComponent) {
          case 'PROFILE' : return <Profile />
          case 'PROJECTS' : return <Projects />
        }
      }

    render() {
        return (
            <>
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <a class="navbar-brand" href="#">Crowdfunding</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link" href="#" onClick={() => this.assignComponent('PROFILE')}>Profile</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="#" onClick={() => this.assignComponent('PROJECTS')}>Projects</a>
                            </li>

                        </ul>
                    </div>
                </div>
            </nav>
            {this.getComponent()}
            </>
        )
    }
}