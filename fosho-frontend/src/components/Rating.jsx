import PropTypes from "prop-types";
import { useState } from "react";
import { Rating as StarRating } from "react-simple-star-rating";

const Rating = ({ defaultValue, disable }) => {
  const [rating, setRating] = useState(defaultValue || 0);

  const handleRating = (rate) => {
    setRating(rate);
  };

  return (
    <StarRating
      onClick={handleRating}
      initialValue={rating}
      readonly={disable}
      transition
      allowFraction
      iconsCount={5}
      size={20}
      SVGclassName="inline"
    />
  );
};

Rating.propTypes = {
  defaultValue: PropTypes.number,
  disable: PropTypes.bool,
};

export default Rating;
