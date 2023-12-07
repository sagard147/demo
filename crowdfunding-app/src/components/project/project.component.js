import React, { Component } from "react";
import "./project.scss";

export default class Projects extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projectDonations: [
        {
          id: 1,
          transactionDate: "2023-12-05",
          userName: "DonSam hey",
          fundAmount: 100,
          currency: "INR",
          userId: 3,
          projectId: 1,
          projectName: "Adidas",
        },
        {
          id: 2,
          transactionDate: "2023-12-05",
          userName: "DonSam hey",
          fundAmount: 200,
          currency: "INR",
          userId: 3,
          projectId: 2,
          projectName: "Nike",
        },
        {
          id: 3,
          transactionDate: "2023-12-05",
          userName: "DonRam bye",
          fundAmount: 200,
          currency: "INR",
          userId: 4,
          projectId: 1,
          projectName: "Adidas",
        },
        {
          id: 4,
          transactionDate: "2023-12-05",
          userName: "DonSam hey",
          fundAmount: 500,
          currency: "INR",
          userId: 3,
          projectId: 4,
          projectName: "Run8",
        },
      ],
      showAddDonationForm: false,
      addDonationFormError: false,
      donationAmount: null,
    };
  }

  componentDidMount = () => {
    // API call
    // setstate  - projectDonations
    const { selectedProject, user } = this.props;
    var url = "http://localhost:8080/api/v1/donations/project/"+selectedProject.id;
    const fetchDonations = async (onSuccess) => {
          try {
            const response = await fetch(url);
            if (!response.ok) {
              throw new Error("Network response was not ok");
            }
            const result = await response.json();
            console.log(result);
            debugger;
            onSuccess(result);
          } catch (error) {
            console.log("Network response was not ok", error);
          }
        };
        fetchDonations((projects) =>{
            if(user.accountType === "DONOR"){
                projects = projects.filter((project)=>project.userId === user.id);
            }
            console.log(projects);
            this.setState({ projectDonations: projects });
        });

  };

  addDonationHandler = () => {
    this.setState({
      showAddDonationForm: true,
    });
  };

  addDonationInputChangeHandler = (e) => {
    const { selectedProject } = this.props;
    this.setState(
      {
        addDonationFormError: Number(e?.target?.value) + selectedProject.fundCollected > selectedProject.fundDemand,
        donationAmount: Number(e?.target?.value)
      }
    );
  };

  addNewDonationHandler = () => {
    const { selectedProject, user } = this.props;
    const {projectDonations, addDonationFormError, donationAmount} = this.state;
    if (!addDonationFormError) {
        const addDonation = async (onSuccess) => {
              try {
                const response = await fetch("http://localhost:8080/api/v1/donations/addDonation", {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                  },
                  body: JSON.stringify({
                    projectId: selectedProject.id,
                    userId: user.id,
                    fundAmount: this.state.donationAmount,
                    currency:"INR"
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


        addDonation((result) => {
              console.log("Donation added successfully",result);
              result.transactionDate = new Date(result.transactionDate).toISOString().split('T')[0];
              let projectDonationsArr = projectDonations;
                      projectDonationsArr.push(result);
                    this.setState({
                      projectDonations: projectDonationsArr,
                      donationAmount: ''
                    });
            });
    }
  }

  render() {
    const { selectedProject, backHandler, user } = this.props;
    const { projectDonations, showAddDonationForm, addDonationFormError } =
      this.state;
    return (
      <>
        <div class="selectedproject-card card">
          <div class="card-body">
            <h3 class="card-title">{selectedProject.title}</h3>
            <div class="container selectedproject-container">
              <div class="row">
                <div class="col">
                  <strong>Description</strong>
                </div>
                <div class="col-8">{selectedProject.description}</div>
              </div>
              <br />
              <div class="row">
                <div class="col">
                  <strong>Fund Demand</strong>
                </div>
                <div class="col-8">
                  {selectedProject.currency} {selectedProject.fundDemand}
                </div>
              </div>
              <br />
              <div class="row">
                <div class="col">
                  <strong>Fund Allocated</strong>
                </div>
                <div class="col-8">
                  {selectedProject.currency} {selectedProject.fundCollected}
                </div>
              </div>
              <br />
              {user.role === "INNOVATOR" && (
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
                          <th scope="row">{projectDonation.transactionDate}</th>
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
              {user.role === "DONOR" && (
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
                          <th scope="row">{projectDonation.transactionDate}</th>
                          <td>
                            {projectDonation.currency}{" "}
                            {projectDonation.fundAmount}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
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
                            <button type="button" class="btn btn-secondary" onClick={this.addNewDonationHandler}>
                              Add
                            </button>
                          </div>
                        </div>
                        <br />
                        <div class="row">
                          {addDonationFormError && (
                            <div class="alert alert-danger" role="alert">
                              Donation amount exceeds the required amount for
                              the project
                            </div>
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
