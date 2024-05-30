import axios from "axios";

async function fetchPublicGet(url) {
  const response = await axios.get(url);
  const { status, data: axiosData } = response;
  if (status === 401) {
    return "";
  }
  return axiosData.data;
}

async function fetchWithAccessTokenAndCredentials(url, method, accessToken) {
  const response = await axios.post(url, {
    withCredentials: true,
    method: method,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
  return response;
}

async function fetchWithAccessToken(url, method, accessToken, payload) {
  const response = await axios.request(url, {
    method: method,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    data: payload,
  });
  return response;
}

export {
  fetchPublicGet,
  fetchWithAccessToken,
  fetchWithAccessTokenAndCredentials,
};
