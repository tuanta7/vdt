import PropTypes from "prop-types";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useParams } from "react-router-dom";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import Map, { Marker } from "react-map-gl";
import toast from "react-hot-toast";
import LoadingButton from "../../components/LoadingButton";
import useGlobal from "../../hooks/useGlobal";

import { MAPBOX_TOKEN, BASE_URL } from "../../utils/constant";
import axios from "axios";
import { fetchWithAccessToken } from "../../utils/fetchFn";

const RestaurantCreate = ({ cancel }) => {
  const {
    info: { accessToken },
  } = useGlobal();
  const { userId } = useParams();
  const queryClient = useQueryClient();
  const {
    register,
    handleSubmit,
    formState: { errors },
    getValues,
    setValue,
  } = useForm();

  const { mutate: findCoords } = useMutation({
    mutationFn: () => {
      if (!getValues("address")) {
        toast.error("Bạn chưa nhập địa chỉ");
        return;
      }

      const address = getValues("address");
      return axios.get(
        `https://api.mapbox.com/search/geocode/v6/forward?q=${address}&access_token=${MAPBOX_TOKEN}`
      );
    },
    onSuccess: (data) => {
      console.log(data);
      setCoordinates({
        long: data.data.features[0].geometry.coordinates[0],
        lat: data.data.features[0].geometry.coordinates[1],
      });
      setViewState({
        longitude: data.data.features[0].geometry.coordinates[0],
        latitude: data.data.features[0].geometry.coordinates[1],
      });
      setValue("longitude", data.data.features[0].geometry.coordinates[0]);
      setValue("latitude", data.data.features[0].geometry.coordinates[1]);
    },
  });

  const { mutate, isPending } = useMutation({
    mutationFn: (payload) =>
      fetchWithAccessToken(
        `${BASE_URL}/restaurants`,
        "POST",
        accessToken,
        payload
      ),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["user-restaurants", userId],
      });
      toast.success("Thêm cửa hàng thành công");
      cancel();
    },
  });

  const [coordinates, setCoordinates] = useState({
    long: parseFloat(localStorage.getItem("long")) || 105,
    lat: parseFloat(localStorage.getItem("lat")) || 22,
  });

  const [viewState, setViewState] = useState({
    longitude: coordinates.long,
    latitude: coordinates.lat,
    zoom: 15,
  });

  const onSubmit = (payload) => {
    mutate(payload);
    console.log(payload);
  };

  const form = (
    <div className="flex-1 flex flex-col justify-between">
      <h2 className="font-semibold mb-3 pl-1 text-primary">
        Thêm cửa hàng mới
      </h2>
      <label className="form-control mb-2">
        <div className="label">
          <span className="label-text">Tên cửa hàng</span>
          <p className="text-red-600 text-sm">{errors?.name?.message}</p>
        </div>
        <input
          type="text"
          className="input input-sm input-bordered"
          {...register("name", {
            required: "Tên không được để trống",
          })}
        />
      </label>
      <label className="form-control mb-2 w-full">
        <div className="label">
          <span className="label-text">
            Địa chỉ chi tiết{" "}
            <span className="text-xs text-neutral-500">
              (VD: AEON Mall, Đường Cổ Linh, Quận Long Biên, Thành phố Hà Nội)
            </span>
          </span>

          <p className="text-red-600 text-sm">{errors?.address?.message}</p>
        </div>
        <input
          type="text"
          className="input input-sm input-bordered"
          {...register("address", {
            required: "Địa chỉ không được để trống",
          })}
        />
      </label>
      <div className="grid grid-cols-3 gap-3">
        <label className="form-control mb-2">
          <div className="label">
            <span className="label-text">Điện thoại</span>
            <p className="text-red-600 text-sm">{errors?.phone?.message}</p>
          </div>
          <input
            type="text"
            className="input input-sm input-bordered"
            {...register("phone", {
              required: "*",
              pattern: {
                value: /^[0-9]{10}$/,
                message: "Số điện thoại không hợp lệ",
              },
            })}
          />
        </label>
        <label className="form-control mb-2">
          <div className="label">
            <span className="label-text">Giờ mở</span>
            <p className="text-red-600 text-sm">{errors?.open_time?.message}</p>
          </div>
          <input
            type="time"
            className="input input-sm input-bordered"
            {...register("open_time", {
              required: "*",
            })}
          />
        </label>
        <label className="form-control mb-2">
          <div className="label">
            <span className="label-text">Giờ đóng</span>
            <p className="text-red-600 text-sm">
              {errors?.close_time?.message}
            </p>
          </div>
          <input
            type="time"
            className="input input-sm input-bordered"
            {...register("close_time", {
              required: "*",
            })}
          />
        </label>
      </div>
      <div className="flex flex-row-reverse gap-3 mt-4">
        <button
          className="btn btn-primary text-base-100 rounded-lg btn-sm"
          type="submit"
          disabled={isPending}
        >
          <LoadingButton isLoading={isPending}>Xác nhận</LoadingButton>
        </button>
        <button
          className="btn btn-ghost rounded-lg btn-sm"
          type="reset"
          onClick={cancel}
        >
          Hủy
        </button>
      </div>
    </div>
  );

  const map = (
    <div className="border rounded-xl max-h-[230px] max-w-[400px] overflow-hidden">
      <Map
        mapLib={import("mapbox-gl")}
        mapboxAccessToken={MAPBOX_TOKEN}
        {...viewState}
        onMove={(evt) => setViewState(evt.viewState)}
        style={{ width: 400, height: 300 }}
        mapStyle="mapbox://styles/tran-anhtuan/clwt3dnps01b101qrc1nb8ed3"
        onClick={(e) => {
          console.log(e.lngLat);
          setCoordinates({
            long: e.lngLat.lng,
            lat: e.lngLat.lat,
          });
          setViewState({
            longitude: e.lngLat.lng,
            latitude: e.lngLat.lat,
          });
          setValue("longitude", e.lngLat.lng);
          setValue("latitude", e.lngLat.lat);
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
    </div>
  );

  return (
    <form
      className="p-3 mb-3 border border-neutral-400 rounded-xl"
      onSubmit={handleSubmit(onSubmit)}
      autoComplete="on"
    >
      <div className="flex flex-wrap gap-6 justify-between">
        {form}
        <div className="flex flex-col gap-3">
          {map}
          <div className="grid grid-cols-2 gap-3">
            <input
              type="text"
              className="input input-sm input-bordered"
              placeholder="Kinh độ"
              {...register("longitude", {
                required: "*",
              })}
              disabled
            />
            <input
              type="text"
              className="input input-sm input-bordered"
              placeholder="Vĩ độ"
              {...register("latitude", {
                required: "*",
              })}
              disabled
            />
          </div>
          <button
            role="button"
            className="btn btn-sm"
            onClick={() => findCoords()}
          >
            Tìm địa chỉ trên bản đồ
          </button>
        </div>
      </div>
    </form>
  );
};
RestaurantCreate.propTypes = {
  cancel: PropTypes.func.isRequired,
};

export default RestaurantCreate;
