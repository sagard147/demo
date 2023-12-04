import React, { Component } from 'react'
import './projects.css'

export default class Projects extends Component {

    constructor(props) {
        super(props);
        this.state = {
            projects: [{
                id: 123,
                title: 'ABc',
                description: "Some quick example text to build on the card title and make up the bulk of the card's content. Some quick example text to build on the card title and make up the bulk of the card's content. Some quick example text to build on the card title and make up the bulk of the card's content",
                fundDemand: 20000
            },
            {
                id: 123,
                title: 'ABc',
                description: "Some quick example text to build on the card title and make up the bulk of the card's content",
                fundDemand: 5000
            },
            {
                id: 123,
                title: 'ABc',
                description: "Some quick example text to build on the card title and make up the bulk of the card's content",
                fundDemand: 30000
            },
            {
                id: 123,
                title: 'ABc',
                description: "Some quick example text to build on the card title and make up the bulk of the card's content",
                fundDemand: 20000
            },
            {
                id: 123,
                title: 'ABc',
                description: "Some quick example text to build on the card title and make up the bulk of the card's content",
                fundDemand: 5000
            },
            {
                id: 123,
                title: 'ABc',
                description: "Some quick example text to build on the card title and make up the bulk of the card's content",
                fundDemand: 30000
            },
            ]
        }
    }

    render() {
        return (
            <div className='card-container'>
                {
                    this.state.projects.map((project) => <div class="card project-card">
                        <div class="card-body">
                            <h5 class="card-title">{project.title}</h5>
                            <p class="card-text">{project.description}</p>
                            <h6 class="card-subtitle mb-2 text-muted">Fund Demand: INR {project.fundDemand}</h6>
                        </div>
                    </div>)
                }
            </div>
        )
    }
}