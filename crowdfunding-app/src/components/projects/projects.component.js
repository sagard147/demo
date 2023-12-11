import React, { Component } from "react";
import Project from "../project/project.component";
import AddOrEditProjectForm from "../project/addoreditproject.component";
import "./projects.scss";
import {
  ACCOUNT_TYPE,
  PROJECT_FILTER,
  GENERIC_ERROR_MESSAGE,
  CROWDFUND_API_ENDPOINT,
  CROWDFUND_API_KEY,
  PROJECTS_PER_PAGE,
} from "../../helpers/constants";
import { Alert } from "../../helpers/components/Alert";

export default class Projects extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projects: [],
      projectsToDisplay: [],
      searchValue: "",
      filteredProjects: [],
      selectedProject: undefined,
      showAddOrEditProjectForm: false,
      filter: "ALL",
      selectedPage: 1,
    };
  }

  getProjectsList = () => {
    const { user } = this.props;
    var url = CROWDFUND_API_ENDPOINT + "/projects";
    if (user.accountType === ACCOUNT_TYPE.INNOVATOR) {
      url += "?userId=" + user.id;
    }
    const fetchProjects = async (onSuccess) => {
      try {
        const response = await fetch(url,{
            headers: {
              "Content-Type": "application/json",
              "Api-Key": CROWDFUND_API_KEY
            },
          });
        if (!response.ok) {
          throw response.text();
        }
        const result = await response.json();
        console.log(result);
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
    fetchProjects((result) => {
      this.setState({
        projects: result,
      }, ()=> {this.handlePageChange(1)});
    });
  };

  componentDidMount = () => {
    this.getProjectsList();
  };

  getActiveProjects = (projects) => {
    return projects.filter(
      (project) => project.fundCollected < project.fundDemand
    );
  }

  getArchivedProjects = (projects) => {
    return projects.filter(
      (project) => project.fundCollected >= project.fundDemand
    );
  }

  handleSearchBarChange = (e) => {
    const { projects, filter } = this.state;
    const searchValue = e?.target?.value;
    let filteredProjects = [];
      let projectsToFilter = [];
      switch (filter) {
        case "ALL":
          projectsToFilter = projects;
          break;
        case "ACTIVE":
          projectsToFilter = this.getActiveProjects(projects);
          break;
        case "ARCHIVED":
          projectsToFilter =  this.getArchivedProjects(projects);
          break;
        default:
          projectsToFilter = projects;
      }
      filteredProjects = projectsToFilter.filter((project) =>
        project?.title.toLowerCase().includes(searchValue.toLowerCase())
      );
      this.setState({
        searchValue: e?.target?.value,
        filteredProjects: filteredProjects
      }, () => {this.handlePageChange(1)});
    
    
  };

  handleFilterChange = (filterValue) => {
    const { projects } = this.state;
    let projectsToFilter = []
    this.setState({filter: filterValue}, () => {
      switch (filterValue) {
        case PROJECT_FILTER.ALL:
          projectsToFilter = projects;
          break;
        case PROJECT_FILTER.ACTIVE:
          projectsToFilter = this.getActiveProjects(projects);
          break;
        case PROJECT_FILTER.ARCHIVED:
          projectsToFilter = this.getArchivedProjects(projects);
          break;
        default:
          projectsToFilter = projects;
      }

      this.setState({filteredProjects: projectsToFilter}, () => {this.handlePageChange(1);})
      
    })
  };

  selectProject = (project) => {
    this.setState({
      selectedProject: project,
    });
  };

  handleBackToProjects = () => {
    this.getProjectsList();
    this.setState({
      selectedProject: undefined,
      showAddOrEditProjectForm: false,
    });
  };

  addOrEditProjectClickHandler = () => {
    this.setState({
      showAddOrEditProjectForm: true,
    });
  };

  handlePageChange = (page) => {
    const {projects, filter, searchValue, filteredProjects} = this.state;
    const startIndex = (page - 1) * PROJECTS_PER_PAGE;
    const endIndex = startIndex + PROJECTS_PER_PAGE;
    let finalProjectsToDisplay = filter === PROJECT_FILTER.ALL && !searchValue ? projects : filteredProjects;
    this.setState({
      projectsToDisplay: finalProjectsToDisplay.slice(startIndex, endIndex),
      selectedPage: page,
      totalPages : Math.ceil(finalProjectsToDisplay.length / PROJECTS_PER_PAGE),
    }) 
    
  }

  getPaginationItems = () => {
    const { totalPages } = this.state;

    const paginationItems = [];
    for (let i = 1; i<= totalPages; i++) {
      paginationItems.push(
        <li class="page-item">
          <a class="page-link" href="#" onClick={() => this.handlePageChange(i)} >{i}</a>
        </li>
      )
    }
      return paginationItems;
  }

  render() {
    const {  projects, filter, selectedProject, showAddOrEditProjectForm, errorMessage, totalPages, selectedPage, projectsToDisplay, filteredProjects, searchValue } =
      this.state;
    const { user } = this.props;
    const finalProjects = projectsToDisplay;
    return (
      <>
        {errorMessage && (
          <div class="container">
            <div class="row">
              <Alert errorMessage={errorMessage} />
            </div>
          </div>
        )}
        {!errorMessage && (
          <>
            {selectedProject && !showAddOrEditProjectForm && (
              <Project
                selectedProject={selectedProject}
                backHandler={this.handleBackToProjects}
                editProject={this.addOrEditProjectClickHandler}
                user={this.props.user}
              />
            )}
            {showAddOrEditProjectForm && (
              <AddOrEditProjectForm
                user={user}
                backHandler={this.handleBackToProjects}
                selectedProject={selectedProject}
              />
            )}
            {!selectedProject && !showAddOrEditProjectForm && (
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
                  <div class="col-3">
                    <div class="dropdown">
                      <button
                        class="btn btn-secondary dropdown-toggle"
                        type="button"
                        id="dropdownMenu2"
                        data-bs-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false"
                      >
                        {filter === PROJECT_FILTER.ALL && 'All Projects'}
                        {filter === PROJECT_FILTER.ACTIVE && 'Active Projects'}
                        {filter === PROJECT_FILTER.ARCHIVED && 'Archived Projects'}
                      </button>
                      <div
                        class="dropdown-menu"
                        aria-labelledby="dropdownMenu2"
                      >
                        <button
                          class="dropdown-item"
                          type="button"
                          onClick={() =>
                            this.handleFilterChange(PROJECT_FILTER.ALL)
                          }
                        >
                          All projects
                        </button>
                        <button
                          class="dropdown-item"
                          type="button"
                          onClick={() =>
                            this.handleFilterChange(PROJECT_FILTER.ACTIVE)
                          }
                        >
                          Active projects
                        </button>
                        <button
                          class="dropdown-item"
                          type="button"
                          onClick={() =>
                            this.handleFilterChange(PROJECT_FILTER.ARCHIVED)
                          }
                        >
                          Archived projects
                        </button>
                      </div>
                    </div>
                  </div>

                  {/* Pagination Section */}
                  {totalPages > 1 && (
                    <div class="col-2">
                      <ul class="pagination justify-content-center">
                        <li class={`page-item ${selectedPage === 1 ? "disabled" : ''}`}>
                          <a class="page-link" href="#" tabindex="-1" onClick={() => this.handlePageChange(selectedPage - 1)}>
                            Previous
                          </a>
                        </li>
                        {this.getPaginationItems()}
                        <li class={`page-item ${selectedPage === totalPages ? "disabled" : ''}`}>
                          <a class="page-link" href="#" onClick={() => this.handlePageChange(selectedPage + 1)}>
                            Next
                          </a>
                        </li>
                      </ul>
                      <h6 class="text-muted total-projects">Total projects: {filter === PROJECT_FILTER.ALL && !searchValue ? projects.length : filteredProjects.length}</h6>
                    </div>
                  )}

                  {/* Show add projects only to Innovator */}
                  {user.role === ACCOUNT_TYPE.INNOVATOR && (
                    <div class="col buttons-container">
                      <button
                        type="button"
                        class="btn btn-light"
                        onClick={this.addOrEditProjectClickHandler}
                      >
                        Add project
                      </button>
                    </div>
                  )}
                </div>
                <div class="row">
                  {finalProjects.length === 0 && (
                    <h5 class="text-muted">
                      There are no projects for the selected criteria.{" "}
                      {user.role === ACCOUNT_TYPE.INNOVATOR &&
                        `Use Add Projects button to add projects`}{" "}
                      {user.role === ACCOUNT_TYPE.DONOR &&
                        `Please check back later.`}{" "}
                    </h5>
                  )}
                  <div className="card-container">
                    {finalProjects.map((project) => (
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
                                class={`progress-bar progress-bar-striped ${
                                  project.fundCollected >= project.fundDemand
                                    ? "bg-grey"
                                    : "bg-success"
                                }`}
                                role="progressbar"
                                style={{
                                  width: `${
                                    (project.fundCollected /
                                      project.fundDemand) *
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
        )}
      </>
    );
  }
}
