/**
 * Gets session value for the key, if expired, removes the session storage key
 * @param {String} key gets session storage value for key
 * @returns value for the key stored in session storage
 */
export const getSession = (key) => {
  const sessionData = sessionStorage.getItem(key);
  if (sessionData) {
    const parsedData = JSON.parse(sessionData);
    const currentTime = new Date().getTime();

    // Check if the session has not expired
    if (parsedData.expirationTime > currentTime) {
      return parsedData.data;
    } else {
      // Remove the expired session
      sessionStorage.removeItem(key);
    }
  }
  return null;
};

/**
 * Sets session value
 * @param {String} key sets session storage value for key
 * @param {Object} data sets session storage value 
 * @param {Number} expirationMinutes session validity
 */
export const setSession = (key, data, expirationMinutes) => {
    const expirationTime = new Date().getTime() + expirationMinutes * 10 * 1000;
    const sessionData = {
      data,
      expirationTime,
    };
    sessionStorage.setItem(key, JSON.stringify(sessionData));
  };
