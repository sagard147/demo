import React, { Component } from "react";
import {
  CROWDFUND_SESSION_NAME,
  GENERIC_ERROR_MESSAGE,
  CROWDFUND_API_ENDPOINT,
  CROWDFUND_API_KEY,
} from "../../helpers/constants";
import { Alert } from "../../helpers/components/Alert";
import { setSession } from "../../helpers/utils";

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      errorMessage: "",
    };
  }

  handleEmailChange = (event) => {
    this.setState({ errorMessage: "", email: event.target.value });
  };

  handlePasswordChange = (event) => {
    this.setState({ errorMessage: "", password: event.target.value });
  };

  submitHandler = () => {
    const { onSuccessFullSignIn } = this.props;
    const signIn = async (onSuccess) => {
      try {
        const response = await fetch(CROWDFUND_API_ENDPOINT + "/users/signin", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Api-Key": CROWDFUND_API_KEY,
          },
          body: JSON.stringify({
            emailId: this.state.email,
            password: this.state.password,
          }),
        });
        if (!response.ok) {
          throw response.text();
        }
        const result = await response.json();
        console.log(result);
        // Store user information in sessionStorage
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
    signIn((result) => onSuccessFullSignIn(result));
  };

  render() {
    const { errorMessage, email, password } = this.state;
    return (
      <form>
        <h3>Sign In</h3>
        <div className="mb-3">
          <label>Email address</label>
          <input
            type="email"
            className="form-control"
            placeholder="Enter email"
            value={this.state.email}
            onChange={this.handleEmailChange}
            required
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter password"
            value={this.state.password}
            onChange={this.handlePasswordChange}
            required
          />
        </div>
        <div className="d-grid">
          <button
            className="btn btn-primary"
            disabled={!email || !password}
            onClick={() => this.submitHandler()}
          >
            Submit
          </button>
          {errorMessage && <Alert errorMessage={errorMessage} />}
        </div>
      </form>
    );
  }
}
