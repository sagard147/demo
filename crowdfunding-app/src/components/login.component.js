import React, { Component } from 'react'
export default class Login extends Component {

  constructor(props) {
    super(props);

  }

  submitHandler = () => {
    const {onSuccessFullSignIn} = this.props;
    // TODO: Integration with API and call onSuccessFullSignIn on success
    onSuccessFullSignIn()
  }

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
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter password"
          />
        </div>
        <div className="d-grid">
          <button className="btn btn-primary"  onClick={()=>this.submitHandler()}>
            Submit
          </button>
        </div>
      </form>
    )
  }
}