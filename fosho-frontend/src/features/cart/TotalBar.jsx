import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import { useMutation } from "@tanstack/react-query";

import useGlobal from "../../hooks/useGlobal";
import { BASE_URL } from "../../utils/constant";
import { fetchWithAccessToken } from "../../utils/fetchFn";
import { formatPrice } from "../../utils/price";

const TotalBar = ({ total, discount, items, restaurantId }) => {
  const navigate = useNavigate();
  const {
    info: { user, accessToken },
  } = useGlobal();

  const { mutate } = useMutation({
    mutationFn: () =>
      fetchWithAccessToken(
        `${BASE_URL}/restaurants/${restaurantId}/orders`,
        "POST",
        accessToken,
        items
      ),
    onSuccess: (data) => {
      console.log(data);
      navigate(`/users//${user.id}orders/${data.order.id}`);
    },
  });

  return (
    <div className="flex w-full justify-end items-center gap-10 px-3 pt-3 border-t border-neutral-300">
      <div>
        <p>
          <span>Tổng cộng:</span>
          <span className="pl-2 text-primary font-semibold text-lg">
            {formatPrice(total)} <sup>₫</sup>
          </span>
        </p>
        {discount > 0 && (
          <p className="text-neutral-500 mt-1">
            Tiết kiệm {formatPrice(discount)} ₫
          </p>
        )}
      </div>

      <button
        className="btn btn-primary text-base-100"
        onClick={() => {
          console.log(items);
          mutate();
        }}
      >
        Đặt hàng
      </button>
    </div>
  );
};
TotalBar.propTypes = {
  total: PropTypes.number.isRequired,
  discount: PropTypes.number.isRequired,
  items: PropTypes.array.isRequired,
  restaurantId: PropTypes.number.isRequired,
};

export default TotalBar;
