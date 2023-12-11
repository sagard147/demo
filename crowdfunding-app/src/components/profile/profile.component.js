import React, { Component } from "react";
import './profile.css'

export default class Profile extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  render() {
    const { user } = this.props;
    return (
      <>
        <div className="card">
          <img
            src={user.profileImage}
            className="card-img-top"
            alt="User Profile"
          />
          <div className="card-body">
            <h5 className="card-title">{`${user.firstName} ${user.lastName}`}</h5>
            <p className="card-text profile-info">
              <strong>Email:</strong> {user.email}
              <br />
              <strong>Bio:</strong> {user.bio}
              <br/>
              <strong>Address:</strong> {user.address}
              <br/>
              <strong>Account type:</strong>  {user.role.charAt(0).toUpperCase() + user.role.slice(1)}
            </p>
          </div>
        </div>
      </>
    );
  }
}
