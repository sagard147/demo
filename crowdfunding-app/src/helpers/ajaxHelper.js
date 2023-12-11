import { CROWDFUND_API_ENDPOINT, CROWDFUND_SESSION_NAME } from "../helpers/constants";
import { setSession } from '../helpers/utils';
const signInURL = CROWDFUND_API_ENDPOINT + "/users/signin";

const signInHelper = async (onSuccess, mailId, userToken) => {
  try {
    const response = await fetch(signInURL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        emailId: mailId,
        password: userToken,
      }),
    });

    if (!response.ok) {
      throw await response.text();
    }

    const result = await response.json();
    console.log(result);

    // Assuming setSession is a function you have defined somewhere
    setSession(CROWDFUND_SESSION_NAME, result, 10);

    onSuccess(result);
  } catch (error) {
    console.log("Network response was not ok", error);

    // Handle error state or logging as needed
  }
};

export default signInHelper;