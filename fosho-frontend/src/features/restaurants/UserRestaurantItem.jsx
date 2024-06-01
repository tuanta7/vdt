import PropTypes from "prop-types";
const UserRestaurantItem = ({ restaurant }) => {
  return (
    <div className="card card-side bg-base-100 shadow-md border h-fit w-[720px] rounded-xl">
      <figure>
        <img
          src={restaurant.logo_url || "/no-img.png"}
          alt="Movie"
          className="h-full w-[200px] object-cover"
        />
      </figure>
      <div className="card-body px-6 py-4 text-sm">
        <h2 className="card-title">
          {restaurant.name}{" "}
          <span className="text-xs">
            {restaurant.is_open ? "🟢 Đang mở" : "🔴 Đang đóng"}
          </span>
        </h2>
        <p>{restaurant.address}</p>
        <p className="mr-2">
          {restaurant.rating ? (
            `Đánh giá: ${restaurant.rating}⭐`
          ) : (
            <span className="text-neutral-400">Chưa có đánh giá nào</span>
          )}
        </p>
        <button className="link text-primary no-underline hover:underline max-w-fit">
          Chi tiết
        </button>
      </div>
    </div>
  );
};
UserRestaurantItem.propTypes = {
  restaurant: PropTypes.object.isRequired,
};

export default UserRestaurantItem;
