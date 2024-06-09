import PropTypes from "prop-types";
import { useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

import useGlobal from "../../hooks/useGlobal";
import { BASE_URL } from "../../utils/constant";
import { formatPrice } from "../../utils/price";
import { fetchWithAccessToken } from "../../utils/fetchFn";
import SelectShippingAddress from "./SelectShippingAddress";
import OrderItem from "./OrderItem";
import { InformationCircleIcon } from "@heroicons/react/24/outline";

const OrderConfirm = ({ items, restaurantId }) => {
  const [shippingAddress, setShippingAddress] = useState(0);

  const total = items.reduce(
    (acc, item) => acc + item.dish.price * item.quantity,
    0
  );
  const discount = items.reduce(
    (acc, item) => acc + item.dish.discount * item.quantity,
    0
  );

  const navigate = useNavigate();
  const {
    info: { user, accessToken },
  } = useGlobal();

  const { mutate } = useMutation({
    mutationFn: (payload) =>
      fetchWithAccessToken(
        `${BASE_URL}/restaurants/${restaurantId}/orders`,
        "POST",
        accessToken,
        payload
      ),
    onSuccess: (data) => {
      navigate(`/users/${user.id}orders/${data.order.id}`);
    },
  });

  const placeOrder = () => {
    console.log("Place order");
    console.log({
      shipping_address_id: shippingAddress,
      items: items.map((item) => item.id),
    });
    // mutate({
    //   shipping_address_id: shippingAddress?.id,
    //   items: items.map((item) => item.id),
    // });
  };

  return (
    <dialog id={`order_confirm_${restaurantId}`} className="modal">
      <div className="modal-box rounded-xl min-w-max">
        <div className="flex justify-between gap-6 max-md:flex-col max-md:items-center">
          <div className="flex flex-col gap-3 w-full">
            <h2 className="text-lg text-primary font-semibold w-full">
              Đơn hàng
            </h2>
            <div className="flex flex-col gap-2 border p-3 rounded-xl md:min-h-[250px] w-full">
              {items.map((item) => (
                <OrderItem key={item.id} item={item} />
              ))}
            </div>
            <div className="form-control w-fit">
              <label className="label cursor-pointer">
                <input
                  type="radio"
                  name={`method_${restaurantId}`}
                  className="radio radio-primary radio-sm"
                  checked
                  disabled
                />
                <span className="label-text text-primary ml-1 font-semibold">
                  Thanh toán khi nhận hàng
                </span>
              </label>
            </div>
          </div>
          <SelectShippingAddress setFn={setShippingAddress} />
        </div>
        <div className="flex items-center gap-3 justify-between mt-3">
          <div>
            <div>
              <span>Tổng:</span>
              <span className="pl-1 text-primary font-semibold text-lg">
                {formatPrice(total - discount)} <sup>₫</sup>{" "}
                <span className="text-neutral-500 text-sm">
                  + phí vận chuyển{" "}
                  <span className="tooltip" data-tip="Phí vận chuyển...">
                    <InformationCircleIcon className="w-4 h-4" />
                  </span>
                </span>
              </span>
            </div>
            {discount > 0 && (
              <p className="text-neutral-500 text-xs">
                Tiết kiệm: {formatPrice(discount)} ₫
              </p>
            )}
          </div>
          <button
            className="btn btn-primary text-base-100"
            onClick={placeOrder}
          >
            Xác nhận
          </button>
        </div>
      </div>
      <form method="dialog" className="modal-backdrop">
        <button id="close_confirm_order">Đóng</button>
      </form>
    </dialog>
  );
};

OrderConfirm.propTypes = {
  items: PropTypes.array.isRequired,
  restaurantId: PropTypes.number.isRequired,
};

export default OrderConfirm;
