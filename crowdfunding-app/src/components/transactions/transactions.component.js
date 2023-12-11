import React, { Component } from "react";
import "./transactions.css";
import {
  GENERIC_ERROR_MESSAGE,
  CROWDFUND_API_ENDPOINT,
  CROWDFUND_API_KEY,
} from "../../helpers/constants";
import { Alert } from "../../helpers/components/Alert";

export default class Transactions extends Component {
  constructor(props) {
    super(props);
    this.state = {
      transactions: [],
      errorMessage: "",
    };
  }

  componentDidMount = () => {
    const { user } = this.props;
    var url = CROWDFUND_API_ENDPOINT + "/donations/user/" + user.id;
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

    fetchDonations((result) => {
      console.log(result);
      this.setState({ transactions: result });
    });
  };

  render() {
    const { transactions, errorMessage } = this.state;
    return (
      <>
        {errorMessage && <Alert errorMessage={errorMessage} />}
        {!errorMessage && transactions.length === 0 && (
          <div class="container">
            <div class="row">
              <center>
                <h6 class="text-muted">
                  <br />
                  There are no transactions currently. Donate to any project and
                  check back later
                </h6>
              </center>
            </div>
          </div>
        )}
        {transactions.length !== 0 && (
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
        )}
      </>
    );
  }
}
