import { useReducer, useRef } from "react";
import PropTypes from "prop-types";
import GlobalContext from "./context/GlobalContext";

function reducer(state, action) {
  switch (action.type) {
    case "SET_USER":
      return { ...state, user: action.payload };
    case "SET_ACCESS_TOKEN":
      return { ...state, accessToken: action.payload };
    case "SET_COORDINATES":
      localStorage.setItem("long", action.payload.long);
      localStorage.setItem("lat", action.payload.lat);
      return { ...state, coordinates: action.payload };
    case "LOGOUT":
      return { ...state, user: null, accessToken: null };
    default:
      return state;
  }
}

const GlobalProvider = ({ children }) => {
  const [info, dispatch] = useReducer(reducer, {
    user: null,
    accessToken: null,
    coordinates: {
      long: parseFloat(localStorage.getItem("long")) || 105.8342,
      lat: parseFloat(localStorage.getItem("lat")) || 21.0278,
    },
  });

  const stompClient = useRef(null);

  return (
    <GlobalContext.Provider value={{ dispatch, info, stompClient }}>
      {children}
    </GlobalContext.Provider>
  );
};

GlobalProvider.propTypes = {
  children: PropTypes.node.isRequired,
};

export default GlobalProvider;
