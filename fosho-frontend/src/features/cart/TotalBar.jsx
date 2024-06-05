import PropTypes from "prop-types";
import { formatPrice } from "../../utils/price";

const TotalBar = ({ total }) => {
  return (
    <div className="flex w-full justify-end px-4">
      <p className="text-lg">
        Tổng cộng:
        <span className="pl-3 text-primary font-semibold">
          {formatPrice(total)} <sup>₫</sup>
        </span>
      </p>
    </div>
  );
};
TotalBar.propTypes = {
  total: PropTypes.number.isRequired,
};

export default TotalBar;
