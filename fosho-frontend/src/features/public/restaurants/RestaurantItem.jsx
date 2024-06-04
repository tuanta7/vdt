import PropTypes from "prop-types";
import { fill } from "../../../utils/image";
import { Link } from "react-router-dom";

const RestaurantItem = ({ restaurant }) => {
  return (
    <div className="card w-[250px] h-[450px] border-2 border-base-300 rounded-2xl overflow-hidden">
      <Link to={`/restaurants/${restaurant.id}`}>
        <figure>
          <img
            src={fill(restaurant.logo_url, 400, 400) || "/no-img.png"}
            className="h-[250px] object-center object-cover w-full"
          />
        </figure>
        <div className="px-3 pt-2 border-t-2 border-base-300  flex flex-col justify-evenly gap-2">
          <div className="h-12 flex items-center">
            <div className="">
              <h2 className="font-semibold text-lg truncate max-w-[200px]">
                {restaurant.name}
              </h2>
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
        </div>
      </Link>
    </div>
  );
};

RestaurantItem.propTypes = {
  restaurant: PropTypes.object,
};

export default RestaurantItem;
