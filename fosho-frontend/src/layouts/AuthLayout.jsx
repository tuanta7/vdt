import { Outlet } from "react-router-dom";
import Footer from "../components/Footer";
const AuthLayout = () => {
  return (
    <div className="w-full flex flex-col h-screen bg-base-100">
      <div className="navbar bg-base-200 px-10">
        <a className="text-xl min-w-24" href="/">
          <img src="/text-logo.png" alt="logo" className="w-24" />
        </a>
      </div>
      <div className="bg-error min-h-[720px] flex justify-evenly items-center">
        <img
          src="/auth-bg.png"
          alt="auth-bg"
          className="h-full object-cover max-lg:hidden"
        />
        <Outlet />
      </div>
      <Footer />
    </div>
  );
};
AuthLayout.propTypes = {};

export default AuthLayout;
