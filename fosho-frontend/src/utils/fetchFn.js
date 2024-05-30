import axios from "axios";

async function fetchPublicGet(url) {
  const response = await axios.get(url);
  const { status, data: axiosData } = response;
  if (status === 401) {
    return "";
  }
  return axiosData.data;
}

async function fetchWithCredentials(url, payload) {
  const response = await axios.post(url, payload, {
    withCredentials: true,
  });
  return response;
}

async function fetchWithAccessToken(url, method, accessToken, payload) {
  const response = await axios.request({
    method: method,
    url: url,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    data: payload,
  });
  return response;
}

export { fetchPublicGet, fetchWithAccessToken, fetchWithCredentials };
