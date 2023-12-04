import React, { Component } from 'react'
import './signup.css'
export default class SignUp extends Component {
  constructor(props) {
    super(props);

  }

  componentDidMount() {
  }

  submitHandler = () => {
    const {onSuccessFullSignIn} = this.props;
    // TODO: Integration with API and call onSuccessFullSignIn on success
    onSuccessFullSignIn()
  }

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
          />
        </div>
        <div className="mb-3">
          <label>Last name</label>
          <input type="text" className="form-control" placeholder="Last name" />
        </div>
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
        <div className='mb-3'>
        <label>Account type</label>
        <div className="radio-box-container">
        <div>
  <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1"/>
  <label class="form-check-label" for="flexRadioDefault1">
   Innovator
  </label>
</div>
<div>
  <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked/>
  <label class="form-check-label" for="flexRadioDefault2">
   Funder
  </label>
</div>
</div>
</div>
        <div className="d-grid">
          <button className="btn btn-primary" onClick={()=>this.submitHandler()}>
            Sign Up
          </button>
        </div>
      </form>
    )
  }
}