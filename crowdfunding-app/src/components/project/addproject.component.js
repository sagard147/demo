import React, { Component } from "react";
import "./project.scss";

export default class AddProjectForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      title: "",
      description: "",
      fundDemand: "",
    };
  }

  addProjectHandler = () => {
    const { user, backHandler } = this.props;
    const addProject = async (onSuccess) => {
      try {
        const response = await fetch("http://localhost:8080/api/v1/projects/addProject", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            title: this.state.title,
            description: this.state.description,
            fundDemand: this.state.fundDemand,
            currency:"INR",
            userId: user.id
          }),
        });

        if (!response.ok) {
          throw new Error("Network response was not ok");
        }

        const result = await response.json();
        onSuccess(result);
      } catch (error) {
        console.log("Network response was not ok", error);
      }
    };

    addProject((result) => {
      console.log("Project added successfully",result);
      backHandler();
    });
  };

  render() {
    const { backHandler } = this.props;

    return (
      <>
        <div className="add-new-project-card card">
          <div className="card-body">
            <h3 className="card-title">Add new project</h3>
            <div className="container add-project-container">
              <div className="row">
                <div className="col">
                  <strong>Project title</strong>
                </div>
                <div className="col-8">
                  <textarea
                    type="textarea"
                    className="form-control"
                    placeholder="Enter your project title"
                    onChange={(e) => this.setState({ title: e.target.value })}
                  />
                </div>
              </div>
              <br />
              <div className="row">
                <div className="col">
                  <strong>Project description</strong>
                </div>
                <div className="col-8">
                  <textarea
                    type="textarea"
                    className="form-control"
                    placeholder="Enter your project description"
                    onChange={(e) => this.setState({ description: e.target.value })}
                  />
                </div>
              </div>
              <br />
              <div className="row">
                <div className="col">
                  <strong>Fund Demand</strong>
                </div>
                <div className="col-8">
                  <input
                    type="number"
                    className="form-control"
                    placeholder="Enter the fund required"
                    onChange={(e) => this.setState({ fundDemand: e.target.value })}
                  />
                </div>
              </div>
              <br />
              <button
                type="button"
                className="btn btn-secondary"
                onClick={this.addProjectHandler}
              >
                Add Project
              </button>
              <br />
              <button
                type="button"
                className="btn btn-secondary"
                onClick={backHandler}
              >
                Back to Projects
              </button>
            </div>
          </div>
        </div>
      </>
    );
  }
}