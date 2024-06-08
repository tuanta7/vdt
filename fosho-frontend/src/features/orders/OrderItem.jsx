import PropTypes from "prop-types";

import { fill } from "../../utils/image";
import { XMarkIcon } from "@heroicons/react/24/outline";
import { formatPrice } from "../../utils/price";

const OrderItem = ({ item }) => {
  return (
    <div className="flex gap-2">
      <img
        src={fill(item.dish.thumbnail_url, 200, 200)}
        className="w-[100px] h-[100px] rounded-lg"
      />
      <div className="min-w-[270px]">
        <h2 className="font-semibold">
          {item.dish.name}{" "}
          <span>
            <XMarkIcon className="w-3 inline" />
            {item.quantity}
          </span>
        </h2>
        <p>
          Giá: {formatPrice(item.dish.price * item.quantity)}
          <sup> ₫</sup>
        </p>
        <p className="text-sm">
          Giảm: {formatPrice(item.dish.discount * item.quantity)}
          <sup> ₫</sup>
        </p>
      </div>
    </div>
  );
};
OrderItem.propTypes = {
  item: PropTypes.object.isRequired,
};

export default OrderItem;
