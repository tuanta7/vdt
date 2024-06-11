import { useState, useEffect } from "react";
import PropTypes from "prop-types";
import Map, { Marker } from "react-map-gl";
import { useQueryClient } from "@tanstack/react-query";

import useGlobal from "../../../hooks/useGlobal";
import { MAPBOX_TOKEN } from "../../../utils/constant";
import Address from "./Address";
import UpdateMapForm from "./UpdateMapForm";

const Mapbox = ({ w, h }) => {
  const queryClient = useQueryClient();
  const { dispatch } = useGlobal();
  const [coordinates, setCoordinates] = useState({
    long: parseFloat(localStorage.getItem("long")) || 105.8342,
    lat: parseFloat(localStorage.getItem("lat")) || 21.0278,
  });

  const [viewState, setViewState] = useState({
    longitude: coordinates.long,
    latitude: coordinates.lat,
    zoom: 16,
  });

  const handleCoordinates = (long, lat) => {
    setCoordinates({ long: long, lat: lat });
    setViewState((prev) => ({ ...prev, longitude: long, latitude: lat }));
    localStorage.setItem("long", long);
    localStorage.setItem("lat", lat);
    dispatch({ type: "SET_COORDINATES", payload: { long, lat } });
    queryClient.invalidateQueries({
      queryKey: ["reverse-geocode"],
    });
  };

  useEffect(() => {
    if (!localStorage.getItem("long") && !localStorage.getItem("lat")) {
      navigator.geolocation.getCurrentPosition((position) => {
        console.log(position);
        handleCoordinates(position.coords.longitude, position.coords.latitude);
      });
    }
    // eslint-disable-next-line
  }, []);

  return (
    <>
      <div className="max-md:hidden">
        <div className="max-w-min">
          <p className="p-2 text-sm border rounded-t-lg word-wrap">
            📍
            <Address long={coordinates.long} lat={coordinates.lat} />
          </p>
          <div className="p-2 mb-2 border rounded-b-lg flex items-center justify-between text-sm">
            <h2 className="text-neutral-600 text-sm">Vị trí không đúng?</h2>
            <button
              className="btn btn-info btn-xs text-base-200"
              onClick={() => document.getElementById("current_map").showModal()}
            >
              Cập nhật
            </button>
          </div>
          <Map
            mapLib={import("mapbox-gl")}
            mapboxAccessToken={MAPBOX_TOKEN}
            {...viewState}
            onMove={(evt) => setViewState(evt.viewState)}
            style={{
              width: w || 230,
              height: h || 400,
              borderRadius: "0.5rem",
            }}
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
      </div>
      <UpdateMapForm
        initCoordinates={coordinates}
        reset={(long, lat) => handleCoordinates(long, lat)}
      />
    </>
  );
};

Mapbox.propTypes = {
  long: PropTypes.number,
  lat: PropTypes.number,
  w: PropTypes.number,
  h: PropTypes.number,
};

export default Mapbox;
