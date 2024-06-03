import { PencilSquareIcon } from "@heroicons/react/24/outline";
import PropTypes from "prop-types";

const UserDishToolbar = ({ dishId }) => {
  return (
    <div>
      <button className="btn btn-sm glass text-xs">
        <PencilSquareIcon className="w-4" /> {dishId}
      </button>
    </div>
  );
};

UserDishToolbar.propTypes = {
  dishId: PropTypes.number.isRequired,
};

export default UserDishToolbar;
