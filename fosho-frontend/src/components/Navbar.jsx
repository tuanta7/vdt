import { PropTypes } from "prop-types";
import { useState } from "react";
import {
  Bars3BottomLeftIcon,
  ShoppingCartIcon,
  MagnifyingGlassIcon,
  ArrowRightEndOnRectangleIcon,
  ChatBubbleBottomCenterTextIcon,
} from "@heroicons/react/24/outline";
import { Link, NavLink, useNavigate } from "react-router-dom";
import useGlobal from "../hooks/useGlobal";
import Notification from "../features/Notification";

const Navbar = ({ UserAvatar }) => {
  const navigate = useNavigate();
  const [search, setSearch] = useState("");
  const {
    info: { user },
  } = useGlobal();
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
        <form
          className="max-md:hidden flex items-center justify-between border rounded-lg bg-base-100 w-full"
          onSubmit={(e) => {
            e.preventDefault();
            if (search) navigate(`/search?q=${search}`);
          }}
        >
          <input
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Tìm nhà hàng, món ăn..."
            className="input input-sm rounded-lg focus:border-none no-focus w-full"
          />
          <button
            className="btn btn-ghost rounded-lg btn-sm"
            onClick={() => {
              if (search) navigate(`/search?q=${search}`);
            }}
          >
            <MagnifyingGlassIcon className="w-4 h-4" />
          </button>
        </form>
      </div>
      <div className="navbar-end flex gap-4 items-center">
        <Notification />
        <NavLink
          to={user ? `/users/${user.id}/cart` : "/auth/login"}
          className="btn btn-ghost btn-circle btn-sm"
        >
          <ShoppingCartIcon className="w-5" />
        </NavLink>
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
