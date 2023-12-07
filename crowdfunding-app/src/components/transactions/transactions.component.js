import React, { Component } from "react";
import './transactions.css';

export default class Transactions extends Component {
  constructor(props) {
    super(props);
    this.state = {
      transactions: [
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
    };
  }

  componentDidMount = () => {
      const { selectedProject, user } = this.props;
      var url = "http://localhost:8080/api/v1/donations/user/"+user.id;
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

          fetchDonations((result) =>{
            console.log(result);
            this.setState({ transactions: result });
            debugger;
          });
    };

  render() {
    const { transactions } = this.state;
    return (
      <div class="container transactions-container">
        <table class="table table-bordered table-light">
          <thead>
            <tr>
              <th scope="col">Date</th>
              <th scope="col">Project Name</th>
              <th scope="col">Donation</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((transaction) => (
              <tr>
                <th scope="row">{transaction.transactionDate}</th>
                <td>{transaction.projectName}</td>
                <td>
                  {transaction.currency} {transaction.fundAmount}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        </div>
    );
  }
}
