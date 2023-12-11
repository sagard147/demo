import React, { Component } from "react";
import {
  GENERIC_ERROR_MESSAGE,
  CROWDFUND_API_ENDPOINT,
  CROWDFUND_API_KEY,
} from "../../helpers/constants";
import "./project.scss";

export default class AddOrEditProjectForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      title: props?.selectedProject?.title || "",
      description: props?.selectedProject?.description || "",
      fundDemand: props?.selectedProject?.fundDemand || "",
      errorMessage: "",
    };
  }

  addOrEditProjectHandler = () => {
    const { user, backHandler, selectedProject } = this.props;
    const apiEndpoint = selectedProject
      ? `/projects/ + ${selectedProject.id}`
      : "/projects/addProject";
    const apiUrl = CROWDFUND_API_ENDPOINT + apiEndpoint;
    const addOrEditProject = async (onSuccess) => {
      try {
        const response = await fetch(apiUrl, {
          method: selectedProject ? "PUT":"POST",
          headers: {
            "Content-Type": "application/json",
            "Api-Key": CROWDFUND_API_KEY,
          },
          body: JSON.stringify({
            title: this.state.title,
            description: this.state.description,
            fundDemand: this.state.fundDemand,
            currency: "INR",
            userId: user.id,
            ...(selectedProject ? { id: selectedProject.id } : {}),
          }),
        });

        if (!response.ok) {
          throw response.text();
        }

        const result = await response.json();
        onSuccess(result);
      } catch (error) {
        console.log("Network response was not ok", error);
        this.setState({ errorMessage: GENERIC_ERROR_MESSAGE });
        error?.then?.((text) => {
          const errorObj = JSON.parse(text);
          this.setState({
            errorMessage: GENERIC_ERROR_MESSAGE + ` ${errorObj?.message}`,
          });
        });
      }
    };

    addOrEditProject((result) => {
      console.log("Project added successfully", result);
      backHandler();
    });
  };

  render() {
    const { backHandler, selectedProject } = this.props;
    const { errorMessage, title, description, fundDemand } = this.state;

    return (
      <>
        <div className="add-new-project-card card">
          <div className="card-body">
            <h3 className="card-title">{selectedProject ? "Edit project" : "Add new project"}</h3>
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
                    onChange={(e) =>
                      this.setState({ errorMessage: "", title: e.target.value })
                    }
                    defaultValue={selectedProject?.title}
                    required
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
                    onChange={(e) =>
                      this.setState({
                        errorMessage: "",
                        description: e.target.value,
                      })
                    }
                    defaultValue={selectedProject?.description}
                    required
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
                    onChange={(e) =>
                      this.setState({
                        errorMessage: "",
                        fundDemand: e.target.value,
                      })
                    }
                    defaultValue={selectedProject?.fundDemand}
                    required
                  />
                </div>
              </div>
              <br />
              <button
                type="button"
                className="btn btn-secondary"
                onClick={this.addOrEditProjectHandler}
                disabled={!title || !description || !fundDemand}
              >
                {selectedProject ? "Edit Project" : "Add Project"}
              </button>
              <br />
              <br />
              <button
                type="button"
                className="btn btn-secondary"
                onClick={backHandler}
              >
                Back to Projects
              </button>
              {errorMessage && (
                <>
                  <br />
                  <br />
                  <div class="alert alert-danger" role="alert">
                    {errorMessage}
                  </div>
                </>
              )}
            </div>
          </div>
        </div>
      </>
    );
  }
}
