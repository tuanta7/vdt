import PropTypes from "prop-types";
import { formatPrice } from "../../utils/price";

const TotalBar = ({ total, discount, items }) => {
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
          console.log("Đặt hàng");
          console.log(items);
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
};

export default TotalBar;
