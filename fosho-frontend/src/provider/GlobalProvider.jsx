import { useReducer } from "react";
import PropTypes from "prop-types";
import GlobalContext from "./context/GlobalContext";

function reducer(state, action) {
  switch (action.type) {
    case "SET_USER":
      return { ...state, user: action.payload };
    case "SET_ACCESS_TOKEN":
      return { ...state, accessToken: action.payload };
    case "SET_COORDINATES":
      localStorage.setItem("long", action.payload.longitude);
      localStorage.setItem("lat", action.payload.latitude);
      return { ...state, coordinates: action.payload };
    default:
      return state;
  }
}

const GlobalProvider = ({ children }) => {
  const [info, dispatch] = useReducer(reducer, {
    user: null,
    accessToken: null,
    coordinates: {
      latitude: null,
      longitude: null,
    },
  });

  return (
    <GlobalContext.Provider value={{ dispatch, info }}>
      {children}
    </GlobalContext.Provider>
  );
};

GlobalProvider.propTypes = {
  children: PropTypes.node.isRequired,
};

export default GlobalProvider;
