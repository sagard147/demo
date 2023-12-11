import React, { Component } from "react";
import "./project.scss";
import {
  ACCOUNT_TYPE,
  GENERIC_ERROR_MESSAGE,
  CROWDFUND_API_ENDPOINT,
  CROWDFUND_API_KEY,
} from "../../helpers/constants";
import { Alert } from "../../helpers/components/Alert";

export default class Project extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projectDonations: [],
      showAddDonationForm: false,
      addDonationFormError: false,
      donationAmount: null,
      errorMessage: "",
      donationFetchErrorMessage: "",
      projectLoadErrorMessage: "",
      currentProject: props.selectedProject,
    };
  }

  updateSelectedProject = () => {
    var url =
      CROWDFUND_API_ENDPOINT + "/projects/" + this.state.currentProject.id;

    const fetchProject = async (onSuccess) => {
      try {
        const response = await fetch(url, {
          headers: {
            "Content-Type": "application/json",
            "Api-Key": CROWDFUND_API_KEY,
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
        this.setState({ projectLoadErrorMessage: GENERIC_ERROR_MESSAGE });
        error?.then?.((text) => {
          const errorObj = JSON.parse(text);
          this.setState({
            projectLoadErrorMessage:
              GENERIC_ERROR_MESSAGE + ` ${errorObj?.message}`,
          });
        });
      }
    };
    fetchProject((result) => {
      this.setState({
        currentProject: result,
      });
    });
  };

  fetchProjectDonations = () => {
    const { user } = this.props;
    const { currentProject } = this.state;
    var url =
      CROWDFUND_API_ENDPOINT + "/donations/project/" + currentProject.id;
    const fetchDonations = async (onSuccess) => {
      try {
        const response = await fetch(url, {
          headers: {
            "Content-Type": "application/json",
            "Api-Key": CROWDFUND_API_KEY,
          },
        });
        if (!response.ok) {
          throw response.text();
        }
        const result = await response.json();
        onSuccess(result);
      } catch (error) {
        console.log("Network response was not ok", error);
        this.setState({ donationFetchErrorMessage: GENERIC_ERROR_MESSAGE });
        error?.then?.((text) => {
          const errorObj = JSON.parse(text);
          this.setState({
            donationFetchErrorMessage:
              GENERIC_ERROR_MESSAGE + ` ${errorObj?.message}`,
          });
        });
      }
    };
    fetchDonations((projects) => {
      if (user.accountType === ACCOUNT_TYPE.DONOR) {
        projects = projects.filter((project) => project.userId === user.id);
      }
      console.log(projects);
      this.setState({ projectDonations: projects });
    });
  };

  componentDidMount = () => {
    this.fetchProjectDonations();
  };

  addDonationHandler = () => {
    this.setState({
      showAddDonationForm: true,
    });
  };

  addDonationInputChangeHandler = (e) => {
    const { currentProject } = this.state;
    this.setState({
      addDonationFormError:
        Number(e?.target?.value) + currentProject.fundCollected >
        currentProject.fundDemand,
      donationAmount: Number(e?.target?.value),
      errorMessage: "",
    });
  };

  addNewDonationHandler = () => {
    const { user } = this.props;
    const { currentProject } = this.state;
    const { addDonationFormError, donationAmount } = this.state;
    if (!addDonationFormError) {
      const addDonation = async (onSuccess) => {
        try {
          const response = await fetch(
            CROWDFUND_API_ENDPOINT + "/donations/addDonation",
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                "Api-Key": CROWDFUND_API_KEY,
              },
              body: JSON.stringify({
                projectId: currentProject.id,
                userId: user.id,
                fundAmount: donationAmount,
                currency: "INR",
              }),
            }
          );

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

      addDonation((result) => {
        console.log("Donation added successfully", result);
        this.fetchProjectDonations();
        this.updateSelectedProject();
      });
    }
  };

  render() {
    const { backHandler, user, editProject } = this.props;
    const { currentProject } = this.state;
    const {
      projectDonations,
      showAddDonationForm,
      addDonationFormError,
      errorMessage,
      donationFetchErrorMessage,
    } = this.state;
    return (
      <>
        <div class="selectedproject-card card">
          <div class="card-body">
            <h3 class="card-title">{currentProject.title}</h3>
            <div class="container selectedproject-container">
              <div class="row">
                <div class="col">
                  <strong>Description</strong>
                </div>
                <div class="col-8">{currentProject.description}</div>
              </div>
              <br />
              <div class="row">
                <div class="col">
                  <strong>Fund Demand</strong>
                </div>
                <div class="col-8">
                  {currentProject.currency} {currentProject.fundDemand}
                </div>
              </div>
              <br />
              <div class="row">
                <div class="col">
                  <strong>Fund Allocated</strong>
                </div>
                <div class="col-8">
                  {currentProject.currency} {currentProject.fundCollected}
                </div>
              </div>
              <br />
              {user.role === ACCOUNT_TYPE.INNOVATOR && (
                <>
                  {projectDonations.length === 0 && (
                    <center>
                      <h6 class="text-muted">
                        There are no donor transactions for this project yet.
                        Please check back later.
                      </h6>
                    </center>
                  )}
                  {projectDonations.length !== 0 && (
                    <>
                      <center>
                        <h5>Transactions</h5>
                      </center>
                      <table class="table table-bordered">
                        <thead>
                          <tr>
                            <th scope="col">Date</th>
                            <th scope="col">Donor Name</th>
                            <th scope="col">Donor Contact</th>
                            <th scope="col">Contribution</th>
                          </tr>
                        </thead>
                        <tbody>
                          {projectDonations.map((projectDonation) => (
                            <tr>
                              <th scope="row">
                                {projectDonation.transactionDate}
                              </th>
                              <td>{projectDonation.userName}</td>
                              <td>{projectDonation.userEmail}</td>
                              <td>
                                {projectDonation.currency}{" "}
                                {projectDonation.fundAmount}
                              </td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </>
                  )}
                  {currentProject.fundCollected === 0 && (
                    <>
                      <button
                        type="button"
                        class="btn btn-secondary"
                        onClick={editProject}
                      >
                        Edit Project
                      </button>
                      &nbsp; &nbsp;
                    </>
                  )}
                </>
              )}
              {user.role === ACCOUNT_TYPE.DONOR && (
                <>
                  {projectDonations.length === 0 && (
                    <>
                      {donationFetchErrorMessage && (
                        <Alert errorMessage={donationFetchErrorMessage} />
                      )}
                      {!donationFetchErrorMessage && (
                        <center>
                          <h6 class="text-muted">
                            You have not made any donations to this project.
                          </h6>
                        </center>
                      )}
                    </>
                  )}
                  {projectDonations.length !== 0 && (
                    <>
                      <center>
                        <h5>Transactions</h5>
                      </center>
                      <table class="table table-bordered">
                        <thead>
                          <tr>
                            <th scope="col">Date</th>
                            <th scope="col">Contribution</th>
                          </tr>
                        </thead>
                        <tbody>
                          {projectDonations.map((projectDonation) => (
                            <tr>
                              <th scope="row">
                                {projectDonation.transactionDate}
                              </th>
                              <td>
                                {projectDonation.currency}{" "}
                                {projectDonation.fundAmount}
                              </td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </>
                  )}
                  {!showAddDonationForm && (
                    <button
                      type="button"
                      class="btn btn-secondary"
                      value={this.state.donationAmount}
                      onClick={this.addDonationHandler}
                    >
                      Add Donation
                    </button>
                  )}

                  {showAddDonationForm && (
                    <>
                      <div class="container add-donation-form-container">
                        <div class="row">
                          <div class="col-8">
                            <input
                              type="number"
                              className="form-control"
                              placeholder="Enter donation amount"
                              onChange={this.addDonationInputChangeHandler}
                            />
                          </div>
                          <div class="col">
                            <button
                              type="button"
                              class="btn btn-secondary"
                              onClick={this.addNewDonationHandler}
                            >
                              Add
                            </button>
                          </div>
                        </div>
                        <br />
                        <div class="row">
                          {errorMessage && (
                            <Alert errorMessage={errorMessage} />
                          )}
                          {addDonationFormError && (
                            <Alert
                              errorMessage="Donation amount exceeds the required amount for
                            the project"
                            />
                          )}
                        </div>
                      </div>
                    </>
                  )}
                  <br />
                  <br />
                </>
              )}
              <button
                type="button"
                class="btn btn-secondary"
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
