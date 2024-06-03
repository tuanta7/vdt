import PropTypes from "prop-types";
import { fill } from "../../../utils/image";

const RestaurantItem = ({ restaurant }) => {
  return (
    <div className="card w-[300px] h-fit glass border border-base-content rounded-2xl">
      <figure>
        <img
          src={fill(restaurant.logo_url, 300, 200) || "/no-img.png"}
          className="h-[200px] object-center object-cover w-full"
        />
      </figure>
      <div className="h-[220px] px-4 flex flex-col justify-evenly gap-2">
        <div className="h-12 flex items-center">
          <div>
            <h2 className="font-semibold text-lg">{restaurant.name}</h2>
            <p className="text-xs text-neutral-500">
              {restaurant.rating > 0
                ? `⭐ ${restaurant.rating}/5`
                : "Chưa có đánh giá"}
            </p>
          </div>
        </div>
        <div className="grid text-sm gap-1">
          <p>📍 {restaurant.address}</p>
          <p> {restaurant.isOpen ? "🟢 Đang mở cửa" : "🔴 Chưa mở cửa"}</p>
          <p>☎️ {restaurant.phone}</p>
        </div>
        <div className="card-actions justify-end">
          <button className="btn btn-xs btn-ghost text-primary">
            Xem thêm
          </button>
        </div>
      </div>
    </div>
  );
};

RestaurantItem.propTypes = {
  restaurant: PropTypes.object,
};

export default RestaurantItem;
