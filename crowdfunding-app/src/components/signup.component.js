import React, { Component } from 'react';
import './signup.css';

export default class SignUp extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      bio: '',
      address: '',
      accountType: 'DONOR', // Default account type
    };
  }

  submitHandler = () => {
    const { onSuccessFullSignIn } = this.props;

    const signUp = async (onSuccess) => {
      try {
        const response = await fetch('http://localhost:8080/api/v1/users/signup', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            email: this.state.email,
            password: this.state.password,
            bio: this.state.bio,
            address: this.state.address,
            accountType: this.state.accountType,
          }),
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const result = await response.json();
        onSuccess(result);
      } catch (error) {
        console.log('Network response was not ok', error);
      }
    };
    signUp((result) => onSuccessFullSignIn(result));
  };

  render() {
    return (
      <form>
        <h3>Sign Up</h3>
        <div className="mb-3">
          <label>First name</label>
          <input
            type="text"
            className="form-control"
            placeholder="First name"
            onChange={(e) => this.setState({ firstName: e.target.value })}
          />
        </div>
        <div className="mb-3">
          <label>Last name</label>
          <input
            type="text"
            className="form-control"
            placeholder="Last name"
            onChange={(e) => this.setState({ lastName: e.target.value })}
          />
        </div>
        <div className="mb-3">
          <label>Email address</label>
          <input
            type="email"
            className="form-control"
            placeholder="Enter email"
            onChange={(e) => this.setState({ email: e.target.value })}
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter password"
            onChange={(e) => this.setState({ password: e.target.value })}
          />
        </div>
        <div className="mb-3">
          <label>Bio</label>
          <textarea
            className="form-control"
            placeholder="Enter bio"
            onChange={(e) => this.setState({ bio: e.target.value })}
          />
        </div>
        <div className="mb-3">
          <label>Address</label>
          <textarea
            className="form-control"
            placeholder="Enter address"
            onChange={(e) => this.setState({ address: e.target.value })}
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
                onChange={() => this.setState({ accountType: 'INNOVATOR' })}
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
                onChange={() => this.setState({ accountType: 'DONOR' })}
              />
              <label className="form-check-label" htmlFor="flexRadioDefault2">
                Funder
              </label>
            </div>
          </div>
        </div>
        <div className="d-grid">
          <button className="btn btn-primary" onClick={this.submitHandler}>
            Sign Up
          </button>
        </div>
      </form>
    );
  }
}
