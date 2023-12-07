import React, { Component } from "react";

const setSession = (key, data, expirationMinutes) => {
  const expirationTime = new Date().getTime() + expirationMinutes * 10 * 1000;
  const sessionData = {
    data,
    expirationTime,
  };
  sessionStorage.setItem(key, JSON.stringify(sessionData));
};

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
    };
  }

  handleEmailChange = (event) => {
    this.setState({ email: event.target.value });
  };

  handlePasswordChange = (event) => {
    this.setState({ password: event.target.value });
  };

  submitHandler = () => {
    const { onSuccessFullSignIn } = this.props;
    // TODO: Integration with API and call onSuccessFullSignIn on success
    const signIn = async (onSuccess) => {
      try {
        const response = await fetch(
          "http://localhost:8080/api/v1/users/signin",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              emailId: this.state.email,
              password: this.state.password,
            }),
          }
        );

        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const result = await response.json();
        console.log(result);
        // Store user information in sessionStorage
        setSession("crowdFundUser", result, 10);
        onSuccess(result);
      } catch (error) {
        console.log("Network response was not ok", error);
      }
    };
    signIn((result) => onSuccessFullSignIn(result));
  };

  render() {
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
          />
        </div>
        <div className="d-grid">
          <button
            className="btn btn-primary"
            onClick={() => this.submitHandler()}
          >
            Submit
          </button>
        </div>
      </form>
    );
  }
}
