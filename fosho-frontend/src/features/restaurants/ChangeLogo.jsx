import PropTypes from "prop-types";
import { PencilSquareIcon } from "@heroicons/react/24/outline";

const ChangeLogo = ({ restaurantId }) => {
  return (
    <form className="flex items-center justify-center">
      <label
        htmlFor="avatar-upload"
        className="btn btn-xs glass text-base-content max-w-fit"
      >
        <PencilSquareIcon className="w-4" /> {restaurantId}
      </label>
      <input id="avatar-upload" name="avatar" type="file" className="hidden" />
    </form>
  );
};
ChangeLogo.propTypes = {
  restaurantId: PropTypes.number,
};

export default ChangeLogo;
