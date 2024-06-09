import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import Map, { Marker } from "react-map-gl";

import { MAPBOX_TOKEN } from "../../utils/constant";
import useGlobal from "../../hooks/useGlobal";

const SelectShippingAddress = ({ setFn }) => {
  const [selectedAddress, setSelectedAddress] = useState(null);
  const {
    info: { user },
  } = useGlobal();

  const [coordinates, setCoordinates] = useState({
    long: user?.shipping_address?.get(0).longitude || 106.6883,
    lat: user?.shipping_address?.get(0).latitude || 10.762622,
  });

  const [viewState, setViewState] = useState({
    longitude: coordinates.long,
    latitude: coordinates.lat,
    zoom: 16,
  });

  const handleCoordinates = (long, lat) => {
    setCoordinates({ long: long, lat: lat });
    setViewState({ longitude: long, latitude: lat, zoom: 16 });
  };

  useEffect(() => {
    if (user?.shipping_addresses) {
      setSelectedAddress(user?.shipping_addresses[0]);
      setFn(user?.shipping_addresses[0].id);
    }
  }, [user.shipping_addresses, setFn]);

  return (
    <div className="flex flex-col gap-2 w-full">
      <select
        className="select select-sm select-bordered w-full"
        defaultValue={user?.shipping_addresses ? 0 : -1}
        onChange={(e) => {
          const address = user?.shipping_addresses[e.target.value];
          setFn(address.id);
          setSelectedAddress(address);
          handleCoordinates(address.longitude, address.latitude);
        }}
      >
        <option value={-1} disabled>
          Chọn địa chỉ giao hàng
        </option>
        {user?.shipping_addresses?.map((sa, i) => (
          <option key={i} value={i}>
            {sa.name}
          </option>
        ))}
      </select>
      <div className="text-sm border w-full p-2 rounded-lg">
        <h2 className="font-semibold">🏠 {selectedAddress?.name}</h2>
        <p>
          🧑🏼‍💼 {selectedAddress?.receiver_name} - {selectedAddress?.phone}
        </p>
        <p className="break-words">🗺️ {selectedAddress?.address}</p>
      </div>
      <Map
        mapLib={import("mapbox-gl")}
        mapboxAccessToken={MAPBOX_TOKEN}
        {...viewState}
        onMove={(evt) => setViewState(evt.viewState)}
        style={{
          width: "100%",
          height: "100%",
          minHeight: "200px",
          borderRadius: "0.5rem",
          border: "1px solid #aaa",
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
  );
};
SelectShippingAddress.propTypes = {
  setFn: PropTypes.func.isRequired,
};

export default SelectShippingAddress;
