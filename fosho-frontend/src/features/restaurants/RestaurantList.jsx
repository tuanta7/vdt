import PropTypes from "prop-types";
const RestaurantList = ({ search, location }) => {
  return (
    <div>
      <h1>Search Term: {search}</h1>
      <h2>Location: {location}</h2>
    </div>
  );
};
RestaurantList.propTypes = {
  search: PropTypes.string,
  location: PropTypes.array,
};

export default RestaurantList;
