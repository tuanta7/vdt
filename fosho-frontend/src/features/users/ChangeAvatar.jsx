import PropTypes from "prop-types";
import { Fragment } from "react";
import { PencilSquareIcon } from "@heroicons/react/24/outline";

const ChangeAvatar = ({ userId }) => {
  return (
    <Fragment>
      <button className="btn btn-xs btn-base glass text-base-100 max-w-fit hover:text-base-content">
        <PencilSquareIcon className="w-4" /> {userId}
      </button>
    </Fragment>
  );
};
ChangeAvatar.propTypes = {
  userId: PropTypes.number,
};

export default ChangeAvatar;
