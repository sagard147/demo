import './alert.scss';

export function Alert(props) {
    return <div class="alert alert-danger error-alert" role="alert">
    {props.errorMessage}
  </div>;
  }