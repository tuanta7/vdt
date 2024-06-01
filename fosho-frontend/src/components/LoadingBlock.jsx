import PropTypes from "prop-types";
import toast from "react-hot-toast";
import Skeleton from "../components/Skeleton";

const LoadingBlock = ({ isLoading, error, children }) => {
  if (isLoading) {
    return (
      <div className="flex-1 flex justify-evenly gap-6">
        <Skeleton />
        <Skeleton />
        <Skeleton />
      </div>
    );
  }

  if (error) {
    const serverError = error.response?.data?.message;
    toast.error(serverError || error.message);
    return serverError || error.message;
  }

  return children;
};
LoadingBlock.propTypes = {
  isLoading: PropTypes.bool,
  error: PropTypes.object,
  children: PropTypes.node,
};

export default LoadingBlock;
