import PropTypes from "prop-types";
import { fill } from "../utils/image";
import { formatPrice } from "../utils/price";
import { Link } from "react-router-dom";

const DishItem = ({ dish, buttonBar }) => {
  const renderPrice = () => {
    if (dish.discount) {
      return (
        <div className="flex items-center gap-2 font-semibold text-lg">
          <p className="text-neutral-500 line-through">
            {formatPrice(dish.price)} <sup>₫</sup>
          </p>
          <p className="text-primary">
            {formatPrice(dish.price - dish.discount)} <sup>₫</sup>
          </p>
        </div>
      );
    }
    return (
      <p className="text-primary font-semibold text-lg">
        {formatPrice(dish.price)} <sup>₫</sup>
      </p>
    );
  };

  return (
    <div className="card w-[250px] h-[320px] glass rounded-2xl overflow-hidden">
      <Link to="">
        <figure>
          <img
            src={fill(dish.thumbnail_url, 150, 150) || "/no-img.png"}
            className="h-[150px] object-center object-cover w-full"
          />
        </figure>
        <div className="px-3 mt-3 flex flex-col gap-1">
          <h2 className="font-semibold text-xl truncate">{dish.name}</h2>
          {renderPrice()}
          <p className="text-sm text-neutral-500">
            {dish.rating > 0
              ? `Đánh giá: ⭐ ${dish.rating}/5`
              : "Chưa có đánh giá"}
          </p>
          <p className="text-neutral-500 text-sm">Đã bán: {dish.sold}</p>
        </div>
      </Link>
      <div className="card-actions justify-end p-2">{buttonBar}</div>
    </div>
  );
};

DishItem.propTypes = {
  dish: PropTypes.object.isRequired,
  buttonBar: PropTypes.node,
};

export default DishItem;
