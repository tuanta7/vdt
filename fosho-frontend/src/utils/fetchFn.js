import axios from "axios";

async function fetchPublicGet(url) {
  const response = await axios.get(url);
  const { data: axiosData } = response;
  return axiosData.data;
}

async function fetchWithAccessTokenAndCredentials(url, method, accessToken) {
  const response = await axios.post(
    url,
    {},
    {
      withCredentials: true,
      method: method,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    }
  );
  const { data: axiosData } = response;
  return axiosData.data;
}

async function fetchWithCredentials(url, method) {
  const response = await axios.post(
    url,
    {},
    {
      withCredentials: true,
      method: method,
    }
  );
  const { data: axiosData } = response;
  return axiosData.data;
}

async function fetchWithAccessToken(url, method, accessToken, payload) {
  const response = await axios.request({
    url,
    data: payload,
    method: method,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
  const { data: axiosData } = response;
  return axiosData.data;
}

export {
  fetchPublicGet,
  fetchWithAccessToken,
  fetchWithCredentials,
  fetchWithAccessTokenAndCredentials,
};
