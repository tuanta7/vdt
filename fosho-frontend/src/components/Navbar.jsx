import { PropTypes } from "prop-types";
import {
  Bars3BottomLeftIcon,
  InboxStackIcon,
  ShoppingCartIcon,
  MagnifyingGlassIcon,
} from "@heroicons/react/24/outline";

const Navbar = ({ LoginButton, UserAvatar }) => {
  return (
    <nav className="navbar bg-base-200 text-neutral-600">
      <div className="navbar-start flex items-center gap-6">
        <div className="dropdown">
          <div
            tabIndex={0}
            role="button"
            className="btn btn-ghost btn-circle btn-sm"
          >
            <Bars3BottomLeftIcon className="w-5" />
          </div>
          <ul
            tabIndex={0}
            className="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-200 rounded-box w-52"
          >
            <li>
              <a>About</a>
            </li>
          </ul>
        </div>
        <a className="text-xl min-w-24">
          <img src="/text-logo.png" alt="logo" className="w-24" />
        </a>
      </div>
      <div className="navbar-center">
        <div className="max-sm:hidden flex items-center border rounded-lg bg-base-100">
          <input
            type="text"
            placeholder="Search"
            className="input input-sm rounded-lg focus:border-none no-focus"
          />
          <button className="btn btn-ghost rounded-lg btn-sm">
            <MagnifyingGlassIcon className="w-4 h-4" />
          </button>
        </div>
      </div>
      <div className="navbar-end flex gap-3 items-center">
        <button className="btn btn-ghost btn-circle btn-sm">
          <InboxStackIcon className="w-5" />
        </button>
        <button className="btn btn-ghost btn-circle btn-sm">
          <ShoppingCartIcon className="w-5" />
        </button>
        <div>{LoginButton || UserAvatar}</div>
      </div>
    </nav>
  );
};
Navbar.propTypes = {
  LoginButton: PropTypes.node,
  UserAvatar: PropTypes.node,
};

export default Navbar;
