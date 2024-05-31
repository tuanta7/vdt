import { NavLink } from "react-router-dom";

const Categories = () => {
  return (
    <div className="min-w-fit">
      <ul className="menu gap-3">
        <li>
          <NavLink to="/" className="rounded-lg w-fit">
            🛎️ <p className="max-sm:hidden"> Cửa hàng</p>
          </NavLink>
        </li>
        <li>
          <NavLink to="/dishes" className="rounded-lg">
            🍕 <p className="max-sm:hidden"> Món ăn</p>
          </NavLink>
        </li>
        <li>
          <NavLink to="/drinks" className="rounded-lg">
            🍹 <p className="max-sm:hidden"> Đồ uống</p>
          </NavLink>
        </li>
      </ul>
    </div>
  );
};
Categories.propTypes = {};

export default Categories;
