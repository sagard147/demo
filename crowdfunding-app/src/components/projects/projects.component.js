import React, { Component } from "react";
import Project from "../project/project.component";
import AddProjectForm from "../project/addproject.component";
import "./projects.scss";

export default class Projects extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projects: [
        {
          id: 123,
          title: "ABc",
          description:
            "Some quick example text to build on the card title and make up the bulk of the card's content. Some quick example text to build on the card title and make up the bulk of the card's content. Some quick example text to build on the card title and make up the bulk of the card's content",
          fundDemand: 20000,
          fundCollected: 20000,
          currency: "INR",
        },
        {
          id: 123,
          title: "abc",
          description:
            "Some quick example text to build on the card title and make up the bulk of the card's content",
          fundDemand: 5000,
          fundCollected: 1000,
          currency: "INR",
        },
        {
          id: 123,
          title: "Test project",
          description:
            "Some quick example text to build on the card title and make up the bulk of the card's content",
          fundDemand: 30000,
          fundCollected: 3000,
          currency: "INR",
        },
        {
          id: 123,
          title: "Finance test project",
          description:
            "Some quick example text to build on the card title and make up the bulk of the card's content",
          fundDemand: 20000,
          fundCollected: 2500,
          currency: "INR",
        },
        {
          id: 123,
          title: "Food on wheels",
          description:
            "Some quick example text to build on the card title and make up the bulk of the card's content",
          fundDemand: 5000,
          fundCollected: 1200,
          currency: "INR",
        },
        {
          id: 123,
          title: "Pqr",
          description:
            "Some quick example text to build on the card title and make up the bulk of the card's content",
          fundDemand: 30000,
          fundCollected: 14000,
          currency: "INR",
        },
      ],
      activeProjects: [],
      archivedProjects: [],
      searchValue: "",
      filteredProjects: [],
      selectedProject: undefined,
      showAddProjectForm: false,
      filter: "ALL",
    };
  }

  componentDidMount = () => {
    const { projects } = this.state;
    const { user } = this.props;
    var url = "http://localhost:8080/api/v1/projects";
    if(user.accountType === "INNOVATOR"){
        url+="?userId="+user.id;
    }
    const fetchProjects = async (onSuccess) => {
          try {
            const response = await fetch(url);
            if (!response.ok) {
              throw new Error("Network response was not ok");
            }
            const result = await response.json();
            console.log(result);
            onSuccess(result);
          } catch (error) {
            console.log("Network response was not ok", error);
          }
        };
        fetchProjects((result) =>{
            console.log(result);
            this.setState({ projects: result })
        });
        let activeProjects = projects.filter((project)=>project.fundCollected < project.fundDemand);
        let archivedProjects = projects.filter((project)=>project.fundCollected >= project.fundDemand);
        this.setState({
          activeProjects,
          archivedProjects
        })
  }

  handleSearchBarChange = (e) => {
    const { projects, activeProjects, archivedProjects, filter } = this.state;
    const searchValue = e?.target?.value;
    let filteredProjects = [];
    if (searchValue){
      let projectsToFilter = [];
      switch (filter) {
        case "ALL":
          projectsToFilter = projects;
          break;
        case "ACTIVE":
          projectsToFilter = activeProjects;
          break;
        case "ARCHIVED":
          projectsToFilter = archivedProjects;
          break;
        default:
          projectsToFilter = projects;
      }
      filteredProjects = projectsToFilter.filter((project) =>
        project?.title.toLowerCase().includes(searchValue.toLowerCase())
      );
    }
    else filteredProjects = [];
    this.setState({
      searchValue: e?.target?.value,
      filteredProjects: filteredProjects,
    });
  };

  selectProject = (project) => {
    this.setState({
      selectedProject: project,
    });
  };

  handleBackToProjects = () => {
    this.setState({
      selectedProject: undefined,
      showAddProjectForm: false,
    });
  };

  addProjectClickHandler = () => {
    this.setState({
      showAddProjectForm: true,
    });
  };

  render() {
    const { searchValue, selectedProject, showAddProjectForm } = this.state;
    const { user } = this.props;
    let projects = []
    switch (this.state.filter) {
      case "ALL":
        projects = this.state.projects;
        break;
      case "ACTIVE":
        projects = this.state.activeProjects;
        break;
      case "ARCHIVED":
        projects = this.state.archivedProjects;
        break;
      default:
        projects = this.state.projects;
    }
    projects = searchValue
      ? this.state.filteredProjects
      : projects;
    return (
      <>
        {selectedProject && !showAddProjectForm && (
          <Project
            selectedProject={selectedProject}
            backHandler={this.handleBackToProjects}
            user={this.props.user}
          />
        )}
        {showAddProjectForm && !selectedProject && (
          <AddProjectForm user={user} backHandler={this.handleBackToProjects} />
        )}
        {!selectedProject && !showAddProjectForm && (
          <div class="container">
            <div class="row search-buttons-row">
              <div class="col-6">
                <input
                  class="form-control"
                  type="text"
                  placeholder="Search by title"
                  onChange={this.handleSearchBarChange}
                />
              </div>
              <div class="col-1">
                <div class="dropdown">
                  <button
                    class="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenu2"
                    data-bs-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                  >
                    Filter by
                  </button>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                    <button
                      class="dropdown-item"
                      type="button"
                      onClick={() => this.setState({ filter: "ALL" })}
                    >
                      All projects
                    </button>
                    <button
                      class="dropdown-item"
                      type="button"
                      onClick={() => this.setState({ filter: "ACTIVE" })}
                    >
                      Active projects
                    </button>
                    <button
                      class="dropdown-item"
                      type="button"
                      onClick={() => this.setState({ filter: "ARCHIVED" })}
                    >
                      Archived projects
                    </button>
                  </div>
                </div>
              </div>

              {/* Show add projects only to Innovator */}
              {user.role === "INNOVATOR" && (
                <div class="col buttons-container">
                  <button
                    type="button"
                    class="btn btn-light"
                    onClick={this.addProjectClickHandler}
                  >
                    Add project
                  </button>
                </div>
              )}
            </div>
            <div class="row">
              <div className="card-container">
                {projects.map((project) => (
                  <div class="container h-100">
                    <div
                      class="card project-card"
                      onClick={() => this.selectProject(project)}
                    >
                      <div class="card-body">
                        <h5 class="card-title">{project.title}</h5>
                        <p class="card-text">{project.description}</p>
                        <div class="card-footer text-muted">
                          Fund Demand: INR {project.fundDemand}
                        </div>
                        <div class="progress">
                          <div
                            class={`progress-bar progress-bar-striped ${project.fundCollected >= project.fundDemand ? "bg-grey" : "bg-success"}`}
                            role="progressbar"
                            style={{
                              width: `${
                                (project.fundCollected / project.fundDemand) *
                                100
                              }%`,
                            }}
                            aria-valuenow={project.fundCollected}
                            aria-valuemin="0"
                            aria-valuemax={project.fundDemand}
                          ></div>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}
      </>
    );
  }
}
