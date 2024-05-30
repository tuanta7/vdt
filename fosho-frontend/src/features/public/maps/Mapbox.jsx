import { useState, useEffect } from "react";
import Map, { Marker } from "react-map-gl";
import { MAPBOX_TOKEN } from "../../../utils/constant";
import Address from "./Address";

const Mapbox = () => {
  const [coordinates, setCoordinates] = useState({
    long: localStorage.getItem("long") || 105.933239,
    lat: localStorage.getItem("lat") || 21.035911,
  });

  useEffect(() => {
    navigator.geolocation.getCurrentPosition((position) => {
      localStorage.setItem("long", position.coords.longitude);
      localStorage.setItem("lat", position.coords.latitude);
      setCoordinates({
        long: position.coords.longitude,
        lat: position.coords.latitude,
      });
    });
  }, []);

  return (
    <div className="rounded-xl border pb-4 max-h-[590px] max-w-72 overflow-hidden">
      <p className="p-2 text-sm border-b word-wrap">
        📍
        <Address long={coordinates.long} lat={coordinates.lat} />
      </p>
      <div className="p-2 border-b  flex items-center justify-between text-sm">
        <h2 className="text-neutral-600 text-sm">Vị trí không đúng?</h2>
        <button
          className="btn btn-info btn-xs text-base-200"
          onClick={() =>
            setCoordinates({
              long: 105.933239,
              lat: 21.035911,
            })
          }
        >
          Cập nhật
        </button>
      </div>
      <Map
        mapLib={import("mapbox-gl")}
        mapboxAccessToken={MAPBOX_TOKEN}
        initialViewState={{
          longitude: coordinates.long,
          latitude: coordinates.lat,
          zoom: 16,
        }}
        style={{ width: 300, height: 500 }}
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

export default Mapbox;
