import PropTypes from "prop-types";
import OrderItem from "./OrderItem";
import { BuildingStorefrontIcon } from "@heroicons/react/24/outline";

import { formatISODate, formatPrice } from "../../utils/format";
import OrderStatus from "./OrderStatus";

const Order = ({ order }) => {
  return (
    <div className="mb-3 border border-neutral-300 shadow-md rounded-xl flex flex-col">
      <div className="flex justify-between py-2 px-4">
        <h2 className="font-semibold col-span-4 flex items-center gap-2">
          <BuildingStorefrontIcon className="w-4" />
          <span>{order.restaurant.name}</span>
        </h2>
        <OrderStatus status={order.status} />
      </div>
      <div className="p-4 border-y">
        <OrderItem key={order.items[0].id} item={order.items[0]} />
      </div>
      <div className="flex justify-between items-center p-2">
        <p className="text-sm text-neutral-500">
          {order.items.length} sản phẩm
        </p>
        <p>
          Thành tiền:{" "}
          <span className="text-sm font-semibold text-primary">
            {formatPrice(order.total_price - order.total_discount)}
            <sup> ₫</sup>
          </span>
        </p>
      </div>
      <p className="text-sm text-neutral-500 p-2 mb-1 border-t">
        Đặt {formatISODate(order.created_at)}
      </p>
    </div>
  );
};
Order.propTypes = {
  order: PropTypes.object.isRequired,
};

export default Order;
