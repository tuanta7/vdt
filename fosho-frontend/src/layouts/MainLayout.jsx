import PropTypes from "prop-types";
import Navbar from "../components/Navbar";

const MainLayout = ({ children }) => {
  return (
    <div className="w-full h-screen bg-base-100">
      <Navbar />
      {children}
    </div>
  );
};
MainLayout.propTypes = {
  children: PropTypes.any,
};

export default MainLayout;
