import { PropTypes } from "prop-types";
import {
  Bars3BottomLeftIcon,
  InboxStackIcon,
  ShoppingCartIcon,
  MagnifyingGlassIcon,
  ArrowRightEndOnRectangleIcon,
  ChatBubbleBottomCenterTextIcon,
} from "@heroicons/react/24/outline";
import { Link } from "react-router-dom";

const Navbar = ({ UserAvatar }) => {
  const LoginButton = (
    <Link to={"/auth/login"} className="btn p-1 text-neutral-600 min-w-fit">
      Đăng nhập
      <ArrowRightEndOnRectangleIcon className="w-5" />
    </Link>
  );
  return (
    <nav className="navbar bg-base-200 text-neutral-600">
      <div className="navbar-start flex items-center gap-4">
        <button className="btn btn-ghost btn-sm">
          <Bars3BottomLeftIcon className="w-5" />
        </button>
        <Link to="/" className="text-xl min-w-24">
          <img src="/text-logo.png" alt="logo" className="w-24" />
        </Link>
      </div>
      <div className="navbar-center min-w-content md:min-w-80">
        <div className="max-md:hidden flex items-center justify-between border rounded-lg bg-base-100 w-full">
          <input
            type="text"
            placeholder="Tìm nhà hàng, món ăn..."
            className="input input-sm rounded-lg focus:border-none no-focus w-full"
          />
          <button className="btn btn-ghost rounded-lg btn-sm">
            <MagnifyingGlassIcon className="w-4 h-4" />
          </button>
        </div>
      </div>
      <div className="navbar-end flex gap-4 items-center">
        <button className="btn btn-ghost btn-circle btn-sm">
          <InboxStackIcon className="w-5" />
        </button>
        <button className="btn btn-ghost btn-circle btn-sm">
          <ShoppingCartIcon className="w-5" />
        </button>
        <button className="btn btn-ghost btn-circle btn-sm">
          <ChatBubbleBottomCenterTextIcon className="w-5" />
        </button>
        <div>{UserAvatar || LoginButton}</div>
      </div>
    </nav>
  );
};
Navbar.propTypes = {
  LoginButton: PropTypes.node,
  UserAvatar: PropTypes.node,
};

export default Navbar;
