import { useQuery } from "@tanstack/react-query";
import { NavLink } from "react-router-dom";

import useGlobal from "../../hooks/useGlobal";
import { BASE_URL } from "../../utils/constant";
import { fetchWithAccessToken } from "../../utils/fetchFn";
import Order from "./Order";

const OrderList = () => {
  const {
    info: { user, accessToken },
  } = useGlobal();

  const { data } = useQuery({
    queryKey: ["orders"],
    queryFn: () =>
      fetchWithAccessToken(
        `${BASE_URL}/users/${user.id}/orders`,
        "GET",
        accessToken
      ),
  });

  return (
    <div className="container md:px-10">
      <ul className="menu menu-horizontal justify-around gap-3 mb-4 bg-base-200 w-full rounded-lg">
        <li className="">
          <NavLink to="" className="font-semibold">
            Chờ xác nhận
          </NavLink>
        </li>
        <li>
          <NavLink to="/confirmed" className="font-semibold">
            Đã xác nhận
          </NavLink>
        </li>
        <li>
          <NavLink to="/in-transit" className="font-semibold">
            Đang vận chuyển
          </NavLink>
        </li>
        <li>
          <NavLink to="/delivered" className="font-semibold">
            Giao thành công
          </NavLink>
        </li>
      </ul>
      {data?.orders?.map((o) => (
        <Order key={o.id} order={o} />
      ))}
    </div>
  );
};
OrderList.propTypes = {};

export default OrderList;
