import PropTypes from "prop-types";
import { MinusIcon, PlusIcon } from "@heroicons/react/24/outline";

import { fill } from "../../utils/image";
import { formatPrice } from "../../utils/price";
import CartItemDelete from "./CartItemDelete";

const CartItem = ({ item }) => {
  const renderPrice = () => {
    if (item.dish.discount) {
      return (
        <div className="flex items-center gap-1 font-semibold flex-wrap">
          <p className="text-neutral-500 line-through">
            {formatPrice(item.dish.price)} <sup>₫</sup>
          </p>
          <p className="text-primary">
            {formatPrice(item.dish.price - item.dish.discount)} <sup>₫</sup>
          </p>
        </div>
      );
    }
    return (
      <p className="text-primary font-semibold">
        {formatPrice(item.dish.price)} <sup>₫</sup>
      </p>
    );
  };

  return (
    <div className="flex-1 grid grid-cols-6 place-items-center gap-6">
      <div className="flex gap-3 col-span-2 justify-self-start">
        <div className="h-[100px] w-[100px] rounded-xl overflow-hidden">
          <img src={fill(item.dish.thumbnail_url, 200, 200)} />
        </div>
        <div className="w-[200px]">
          <p className="text-lg font-semibold text-red-600 ">
            {item.dish.name}
          </p>
          <p className="text-sm text-neutral-600 my-1">
            Đơn vị: {item.dish.unit}
          </p>
          <p className="text-sm text-neutral-600">Còn lại: {item.dish.stock}</p>
        </div>
      </div>
      <div className="flex flex-col">{renderPrice()}</div>
      <div className="flex items-center border border-neutral-400 w-fit rounded-lg overflow-hidden">
        <button className="btn btn-sm glass">
          <MinusIcon className="w-3" />
        </button>
        <p className="input input-sm px-4">{item.quantity}</p>
        <button className="btn btn-sm glass">
          <PlusIcon className="w-3" />
        </button>
      </div>
      <p className="text-primary font-semibold">
        {formatPrice(item.quantity * (item.dish.price - item.dish.discount))} ₫
      </p>
      <div>
        <CartItemDelete itemId={item.id} />
      </div>
    </div>
  );
};
CartItem.propTypes = {
  item: PropTypes.object.isRequired,
};

export default CartItem;
