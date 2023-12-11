import React, { Component } from "react";
import {
  ACCOUNT_TYPE,
  GENERIC_ERROR_MESSAGE,
  CROWDFUND_SESSION_NAME,
  CROWDFUND_API_ENDPOINT,
  CROWDFUND_API_KEY,
} from "../../helpers/constants";
import "./signup.css";
import { Alert } from "../../helpers/components/Alert";
import { setSession } from "../../helpers/utils";

export default class SignUp extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      bio: "",
      address: "",
      accountType: ACCOUNT_TYPE.DONOR, // Default account type
      errorMessage: "",
    };
  }

  submitHandler = () => {
    const { onSuccessFullSignIn } = this.props;
    const { firstName, lastName, email, password, bio, address, accountType } =
      this.state;
    const signUp = async (onSuccess) => {
      try {
        const response = await fetch(
          CROWDFUND_API_ENDPOINT + "/users/signup",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Api-Key": CROWDFUND_API_KEY,
            },
            body: JSON.stringify({
              firstName: firstName,
              lastName: lastName,
              email: email,
              password: password,
              bio: bio,
              address: address,
              accountType: accountType,
            }),
          }
        );
        if (!response.ok) {
          throw response.text();
        }

        const result = await response.json();
        setSession(CROWDFUND_SESSION_NAME, result, 10);
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
    signUp((result) => onSuccessFullSignIn(result));
  };

  render() {
    const {
      firstName,
      lastName,
      email,
      password,
      bio,
      address,
      accountType,
      errorMessage,
    } = this.state;
    return (
      <form>
        <h3>Sign Up</h3>
        <div className="mb-3">
          <label>First name</label>
          <input
            type="text"
            className="form-control"
            placeholder="First name"
            onChange={(e) =>
              this.setState({ errorMessage: "", firstName: e.target.value })
            }
            required
          />
        </div>
        <div className="mb-3">
          <label>Last name</label>
          <input
            type="text"
            className="form-control"
            placeholder="Last name"
            onChange={(e) =>
              this.setState({ errorMessage: "", lastName: e.target.value })
            }
            required
          />
        </div>
        <div className="mb-3">
          <label>Email address</label>
          <input
            type="email"
            className="form-control"
            placeholder="Enter email"
            onChange={(e) =>
              this.setState({ errorMessage: "", email: e.target.value })
            }
            required
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter password"
            onChange={(e) =>
              this.setState({ errorMessage: "", password: e.target.value })
            }
            required
          />
        </div>
        <div className="mb-3">
          <label>Bio</label>
          <textarea
            className="form-control"
            placeholder="Enter bio"
            onChange={(e) =>
              this.setState({ errorMessage: "", bio: e.target.value })
            }
            required
          />
        </div>
        <div className="mb-3">
          <label>Address</label>
          <textarea
            className="form-control"
            placeholder="Enter address"
            onChange={(e) =>
              this.setState({ errorMessage: "", address: e.target.value })
            }
            required
          />
        </div>
        <div className="mb-3">
          <label>Account type</label>
          <div className="radio-box-container">
            <div>
              <input
                className="form-check-input"
                type="radio"
                name="flexRadioDefault"
                id="flexRadioDefault1"
                onChange={() =>
                  this.setState({ accountType: ACCOUNT_TYPE.INNOVATOR })
                }
              />
              <label className="form-check-label" htmlFor="flexRadioDefault1">
                Innovator
              </label>
            </div>
            <div>
              <input
                className="form-check-input"
                type="radio"
                name="flexRadioDefault"
                id="flexRadioDefault2"
                defaultChecked
                onChange={() =>
                  this.setState({ accountType: ACCOUNT_TYPE.DONOR })
                }
              />
              <label className="form-check-label" htmlFor="flexRadioDefault2">
                Funder
              </label>
            </div>
          </div>
        </div>
        <div className="d-grid">
          <button
            className="btn btn-primary"
            disabled={
              !firstName ||
              !lastName ||
              !bio ||
              !address ||
              !email ||
              !password ||
              !accountType
            }
            onClick={this.submitHandler}
          >
            Sign Up
          </button>
          {errorMessage && <Alert errorMessage={errorMessage} />}
        </div>
      </form>
    );
  }
}
