import PropTypes from "prop-types";
import { fill } from "../../../utils/image";
import { Link } from "react-router-dom";
import haversine from "haversine-distance";

import useGlobal from "../../../hooks/useGlobal";
import { isOpen } from "../../../utils/isOpen";
import Rating from "../../../components/Rating";

const RestaurantItem = ({ restaurant }) => {
  const {
    info: { coordinates },
  } = useGlobal();

  const distanceInMeters = haversine(
    {
      lat: coordinates.lat,
      lon: coordinates.long,
    },
    {
      lat: restaurant.latitude,
      lon: restaurant.longitude,
    }
  );

  return (
    <div className="card w-[250px] h-[460px] border-2 border-base-300 rounded-2xl overflow-hidden">
      <Link to={`/restaurants/${restaurant.id}`}>
        <figure>
          <img
            src={fill(restaurant.logo_url, 400, 400) || "/no-img.png"}
            className="h-[250px] object-center object-cover w-full"
          />
        </figure>
        <div className="px-3 pt-2 border-t-2 border-base-300  flex flex-col justify-evenly gap-2">
          <div className="h-12 flex items-center">
            <div>
              <h2 className="font-semibold text-lg truncate max-w-[200px]">
                {restaurant.name}
              </h2>
              <p className="text-xs">
                {isOpen(
                  restaurant.is_active,
                  restaurant.open_time,
                  restaurant.close_time
                )
                  ? "🟢 Đang mở cửa"
                  : "🔴 Chưa mở cửa"}
              </p>
            </div>
          </div>

          <div className="grid text-sm gap-2">
            <p>📍 {restaurant.address}</p>
            <p>☎️ {restaurant.phone}</p>
            {restaurant.rating > 0 ? (
              <div className="flex items-end gap-1">
                <Rating defaultValue={restaurant.rating} disable={true} />{" "}
                <span className="text-sm">{restaurant.rating.toFixed(1)}</span>
              </div>
            ) : (
              <p className="text-sm text-neutral-500">⭐ Chưa có đánh giá</p>
            )}
            <p className="text-neutral-500">
              🗺️ Khoảng {((distanceInMeters / 1000) * 1.35).toFixed(2)} km
            </p>
          </div>
        </div>
      </Link>
    </div>
  );
};

RestaurantItem.propTypes = {
  restaurant: PropTypes.object,
};

export default RestaurantItem;
