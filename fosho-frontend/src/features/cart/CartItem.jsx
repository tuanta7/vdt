import PropTypes from "prop-types";

const CartItem = ({ item }) => {
  return <div className="card">{JSON.stringify(item)}</div>;
};
CartItem.propTypes = {
  item: PropTypes.object.isRequired,
};

export default CartItem;
