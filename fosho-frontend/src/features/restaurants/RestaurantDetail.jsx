import { useQuery } from "@tanstack/react-query";
import { Link, useParams } from "react-router-dom";
import Map, { Marker } from "react-map-gl";

import useGlobal from "../../hooks/useGlobal";
import { BASE_URL, MAPBOX_TOKEN } from "../../utils/constant";
import { fetchWithAccessToken } from "../../utils/fetchFn";
import UserDishList from "../dishes/UserDishList";
import ChangeLogo from "./ChangeLogo";

const RestaurantDetail = () => {
  const {
    info: { accessToken },
  } = useGlobal();

  const { userId, restaurantId } = useParams();

  const { data } = useQuery({
    queryKey: ["userRestaurants", userId, restaurantId],
    queryFn: () =>
      fetchWithAccessToken(
        `${BASE_URL}/restaurants/${restaurantId}`,
        "GET",
        accessToken
      ),
  });

  const map = data && (
    <div className="border border-neutral-300 rounded-2xl w-fit h-fit overflow-hidden">
      <Map
        mapLib={import("mapbox-gl")}
        mapboxAccessToken={MAPBOX_TOKEN}
        initialViewState={{
          latitude: data.restaurant.latitude,
          longitude: data.restaurant.longitude,
          zoom: 16,
        }}
        style={{ width: 300, height: 170 }}
        mapStyle="mapbox://styles/tran-anhtuan/clwt3dnps01b101qrc1nb8ed3"
      >
        <Marker
          longitude={data.restaurant.longitude}
          latitude={data.restaurant.latitude}
          anchor="bottom"
        >
          <img src="/marker.png" alt="marker" className="w-6" />
        </Marker>
      </Map>
    </div>
  );

  return (
    <div className="flex-1 overflow-hidden">
      <div className="flex justify-between items-center mb-3">
        <Link to={`/users/${userId}/restaurants`} className="btn btn-sm">
          🔙 Toàn bộ
        </Link>
        <div className="flex gap-3">
          <button className="btn btn-sm">Chỉnh sửa</button>
          <button className="btn btn-sm btn-primary text-base-100">
            {data?.restaurant.is_open ? "Đóng cửa" : "Mở cửa 🧑🏼‍🍳"}
          </button>
        </div>
      </div>
      <div className="flex justify-between items-start gap-6 flex-wrap mb-3">
        <div className="mt-3 flex items-start gap-3">
          <div className="avatar flex flex-col items-end">
            <div className="w-44 h-44 rounded-xl overflow-hidden">
              <img
                src={data?.restaurant?.logo_url || "/no-img.png"}
                alt="Logo"
              />
            </div>
            <div className="-mt-44 mb-36">
              <ChangeLogo />
            </div>
          </div>
          <div>
            <p>
              {data?.restaurant.is_open ? (
                <span className="text-green-500 font-semibold">
                  🟢 Đang mở cửa
                </span>
              ) : (
                <span className="text-red-500 font-semibold">
                  🔴 Chưa mở cửa
                </span>
              )}
            </p>
            <h2 className="font-semibold text-2xl">{data?.restaurant?.name}</h2>
            <h3 className="mb-2">{data?.restaurant?.address}</h3>
            <p className="mb-1">☎️ Điện thoại: {data?.restaurant?.phone}</p>
            <p className="mb-1">
              🕙 Giờ hoạt động: {data?.restaurant?.open_time} -{" "}
              {data?.restaurant?.close_time}
            </p>
            <p className="mr-2">
              {data?.restaurant.rating ? (
                `Đánh giá: ${data?.restaurant?.rating}⭐`
              ) : (
                <span className="text-neutral-400">⭐ Chưa có đánh giá</span>
              )}
            </p>
          </div>
        </div>
        {map}
      </div>
      <UserDishList />
    </div>
  );
};
RestaurantDetail.propTypes = {};

export default RestaurantDetail;
