import { NavLink } from "react-router-dom";
import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";

const Categories = () => {
  return (
    <div className="min-w-fit mt-3">
      <ul className="menu gap-3 bg-base-200 rounded-lg pr-2">
        <li>
          <NavLink to="" className="rounded-lg">
            🛎️ <p className="max-sm:hidden"> Cửa hàng</p>
          </NavLink>
        </li>
        <li>
          <NavLink to="dishes" className="rounded-lg">
            🍕 <p className="max-sm:hidden"> Đồ ăn</p>
          </NavLink>
        </li>
        <li>
          <button
            className="rounded-lg w-fit"
            onClick={() =>
              document.getElementById("sidebar-seacrh").showModal()
            }
          >
            🔍 <p className="max-sm:hidden"> Tìm kiếm</p>
          </button>
        </li>
        <li>
          <button
            className="rounded-lg"
            onClick={() => document.getElementById("current_map").showModal()}
          >
            🌏 <p className="max-sm:hidden"> Vị trí</p>
          </button>
        </li>
      </ul>
      <dialog id="sidebar-seacrh" className="modal">
        <div className="modal-box bg-base-200">
          <h3 className="font-semibold mb-3">
            Hôm nay bạn muốn ăn gì? 🍕🍔🍟🥟
          </h3>
          <div className="flex items-center justify-between border border-base-content rounded-lg bg-base-100 w-full">
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
        <form method="dialog" className="modal-backdrop">
          <button>Đóng</button>
        </form>
      </dialog>
    </div>
  );
};
Categories.propTypes = {};

export default Categories;
