import PropTypes from "prop-types";
import toast from "react-hot-toast";
import Skeleton from "../components/Skeleton";

const LoadingBlock = ({ isLoading, error, children }) => {
  if (isLoading) {
    return (
      <div className="flex justify-between">
        <Skeleton />
        <Skeleton />
        <Skeleton />
      </div>
    );
  }

  if (error) {
    const serverError = error.response?.data?.message;
    toast.error(serverError || error.message);
    return;
  }

  return children;
};
LoadingBlock.propTypes = {
  isLoading: PropTypes.bool,
  error: PropTypes.object,
  children: PropTypes.node,
};

export default LoadingBlock;
