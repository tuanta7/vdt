import PropTypes from "prop-types";

const OrderDetail = ({ order }) => {
  return <div>{JSON.stringify(order.shipping_address.address)}</div>;
};

OrderDetail.propTypes = {
  order: PropTypes.object.isRequired,
};

export default OrderDetail;
