import PropTypes from "prop-types";

const RestaurantItem = ({ restaurant }) => {
  return (
    <div className="card w-[270px] h-fit glass border border-base-content rounded-2xl">
      <figure>
        <img
          src={restaurant?.logo_url || "/no-img.png"}
          className="h-[180px] object-center object-cover w-full"
        />
      </figure>
      <div className="h-[220px] px-4 mt-3 flex flex-col justify-evenly gap-2">
        <div className="h-12 flex items-center">
          <div>
            <h2 className="font-semibold">{restaurant.name}</h2>
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
