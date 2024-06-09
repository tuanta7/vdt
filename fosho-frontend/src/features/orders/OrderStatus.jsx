import PropTypes from "prop-types";
const OrderStatus = ({ status }) => {
  if (status.toUpperCase() === "PENDING") {
    return <span className="text-neutral-600 text-sm">Chờ xác nhận </span>;
  }
  if (status.toUpperCase() === "CONFIRMED") {
    return <span className="text-secondary">Đã xác nhận</span>;
  }
};
OrderStatus.propTypes = {
  status: PropTypes.string.isRequired,
};

export default OrderStatus;
