import PropTypes from "prop-types";
import { Link, useParams } from "react-router-dom";
import { fill } from "../../utils/image";

const UserRestaurantItem = ({ restaurant }) => {
  const { userId } = useParams();

  return (
    <div className="flex items-start bg-base-100 border xl:w-1/2 rounded-xl overflow-hidden">
      <figure className="min-w-[200px]">
        <img src={fill(restaurant.logo_url, 200, 200) || "/no-img.png"} />
      </figure>
      <div className="card-body px-6 py-4 text-sm max-h-[200px] overflow-y-auto">
        <div className="card-title flex flex-wrap items-center gap-2">
          <h2 className="text-lg">{restaurant.name}</h2>
          <p className="text-xs">
            {restaurant.is_open ? "🟢 Đang mở cửa" : "🔴 Chưa mở cửa"}
          </p>
        </div>
        <p>{restaurant.address}</p>
        <p>
          Giờ hoạt động: {restaurant.open_time} - {restaurant.close_time}
        </p>
        <p className="mr-2">
          {restaurant.rating ? (
            `Đánh giá: ${restaurant.rating}⭐`
          ) : (
            <span className="text-neutral-400">Chưa có đánh giá nào</span>
          )}
        </p>
        <Link
          to={`/users/${userId}/restaurants/${restaurant.id}`}
          className="link text-primary no-underline hover:underline max-w-fit"
        >
          Chi tiết
        </Link>
      </div>
    </div>
  );
};
UserRestaurantItem.propTypes = {
  restaurant: PropTypes.object.isRequired,
};

export default UserRestaurantItem;
