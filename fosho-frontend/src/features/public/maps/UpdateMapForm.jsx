import { useState } from "react";
import PropTypes from "prop-types";
import Map, { Marker } from "react-map-gl";
import axios from "axios";
import { useMutation } from "@tanstack/react-query";

import { MAPBOX_TOKEN } from "../../../utils/constant";
import LoadingButton from "../../../components/LoadingButton";

const UpdateMapForm = ({ initCoordinates, reset }) => {
  const [place, setPlace] = useState("");
  const [coordinates, setCoordinates] = useState(initCoordinates);

  const [viewState, setViewState] = useState({
    longitude: coordinates.long,
    latitude: coordinates.lat,
    zoom: 16,
  });

  const handleCoordinates = (long, lat) => {
    console.log(long, lat);
    setCoordinates({
      long: long,
      lat: lat,
    });
    setViewState({
      longitude: long,
      latitude: lat,
      zoom: 16,
    });
    reset(long, lat);
  };

  const { mutate, isPending } = useMutation({
    mutationFn: () => {
      console.log(place);
      if (!place) {
        throw new Error("Vui lòng nhập địa chỉ cụ thể");
      }
      return axios.get(
        `https://api.mapbox.com/search/geocode/v6/forward?q=${place}&access_token=${MAPBOX_TOKEN}`
      );
    },
    onSuccess: (data) => {
      handleCoordinates(
        data.data.features[0].geometry.coordinates[0],
        data.data.features[0].geometry.coordinates[1]
      );
    },
  });

  return (
    <dialog id="current_map" className="modal">
      <div className="modal-box rounded-xl">
        <div className="flex flex-col items-center gap-2">
          <div className="grid grid-cols-2 gap-3 w-full">
            <input
              type="text"
              className="input input-sm input-bordered"
              placeholder="Kinh độ"
              value={coordinates.long}
              disabled
            />
            <input
              type="text"
              className="input input-sm input-bordered"
              placeholder="Vĩ độ"
              value={coordinates.lat}
              disabled
            />
          </div>
          <Map
            mapLib={import("mapbox-gl")}
            mapboxAccessToken={MAPBOX_TOKEN}
            {...viewState}
            onMove={(evt) => setViewState(evt.viewState)}
            style={{
              width: "100%",
              height: 250,
              borderRadius: "0.5rem",
            }}
            mapStyle="mapbox://styles/tran-anhtuan/clwt3dnps01b101qrc1nb8ed3"
            onClick={(e) => {
              handleCoordinates(e.lngLat.lng, e.lngLat.lat);
            }}
          >
            <Marker
              longitude={coordinates.long}
              latitude={coordinates.lat}
              anchor="bottom"
            >
              <img src="/marker.png" alt="marker" className="w-6" />
            </Marker>
          </Map>
          <div className="grid gap-2 w-full">
            <div className="flex justify-between items-center">
              <h2 className="text-success font-semibold pl-1">
                Đang đâu đấy bạn 🚵🏼‍♀️{" "}
                <span className="text-xs text-neutral-400">
                  (Click vào bản đồ để chọn)
                </span>
              </h2>
              <button
                className="btn btn-sm btn-success text-base-100"
                onClick={() => {
                  document.getElementById("close_current_map").click();
                }}
              >
                Xác nhận
              </button>
            </div>
            <input
              type="text"
              className="input input-bordered"
              placeholder="Vị trí"
              name="current_place"
              value={place}
              onChange={(e) => setPlace(e.target.value)}
            />
            <button className="btn" onClick={() => mutate()}>
              <LoadingButton isLoading={isPending}>
                Tìm trên bản đồ
              </LoadingButton>
            </button>
          </div>
        </div>
      </div>
      <form method="dialog" className="modal-backdrop">
        <button
          id="close_current_map"
          onClick={() => {
            handleCoordinates(coordinates.long, coordinates.lat);
          }}
        >
          close
        </button>
      </form>
    </dialog>
  );
};
UpdateMapForm.propTypes = {
  initCoordinates: PropTypes.object,
  reset: PropTypes.func,
};

export default UpdateMapForm;
