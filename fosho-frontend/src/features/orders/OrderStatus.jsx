import PropTypes from "prop-types";
const OrderStatus = ({ status }) => {
  if (status.toUpperCase() === "PENDING") {
    return (
      <span className="text-info text-sm px-2 border border-info rounded-xl">
        Chờ xác nhận
      </span>
    );
  }
  if (status.toUpperCase() === "CONFIRMED") {
    return <span className="text-secondary">Đã xác nhận</span>;
  }
};
OrderStatus.propTypes = {
  status: PropTypes.string.isRequired,
};

export default OrderStatus;
