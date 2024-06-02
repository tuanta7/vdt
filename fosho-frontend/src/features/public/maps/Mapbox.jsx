import { useState, useEffect } from "react";
import PropTypes from "prop-types";
import Map, { Marker } from "react-map-gl";

import useGlobal from "../../../hooks/useGlobal";
import { MAPBOX_TOKEN } from "../../../utils/constant";
import Address from "./Address";

const Mapbox = ({ long, lat, w, h }) => {
  const { dispatch } = useGlobal();
  const [coordinates, setCoordinates] = useState({
    long: parseFloat(localStorage.getItem("long")) || long,
    lat: parseFloat(localStorage.getItem("lat")) || lat,
  });

  const [viewState, setViewState] = useState({
    longitude: coordinates.long,
    latitude: coordinates.lat,
    zoom: 15,
  });

  useEffect(() => {
    navigator.geolocation.getCurrentPosition((position) => {
      console.log(position);
      localStorage.setItem("long", position.coords.longitude);
      localStorage.setItem("lat", position.coords.latitude);
      setCoordinates({
        long: position.coords.longitude,
        lat: position.coords.latitude,
      });
      setViewState({ ...coordinates, zoom: 15 });
      dispatch({ type: "SET_COORDINATES", payload: coordinates });
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className="rounded-xl border border-neutral-400 pb-1 max-h-min max-w-min overflow-hidden">
      <p className="p-2 text-sm border-b word-wrap">
        📍
        <Address long={coordinates.long} lat={coordinates.lat} />
      </p>
      <div className="p-2 border-b  flex items-center justify-between text-sm">
        <h2 className="text-neutral-600 text-sm">Vị trí không đúng?</h2>
        <button className="btn btn-info btn-xs text-base-200">Cập nhật</button>
      </div>
      <Map
        mapLib={import("mapbox-gl")}
        mapboxAccessToken={MAPBOX_TOKEN}
        {...viewState}
        onMove={(evt) => setViewState(evt.viewState)}
        style={{ width: w || 220, height: h || 500 }}
        mapStyle="mapbox://styles/tran-anhtuan/clwt3dnps01b101qrc1nb8ed3"
      >
        <Marker
          longitude={coordinates.long}
          latitude={coordinates.lat}
          anchor="bottom"
        >
          <img src="/marker.png" alt="marker" className="w-6" />
        </Marker>
      </Map>
    </div>
  );
};

Mapbox.propTypes = {
  long: PropTypes.number,
  lat: PropTypes.number,
  w: PropTypes.number,
  h: PropTypes.number,
};

export default Mapbox;
