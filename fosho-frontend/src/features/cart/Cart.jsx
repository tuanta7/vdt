import { useQuery } from "@tanstack/react-query";

import useGlobal from "../../hooks/useGlobal";
import { fetchWithAccessToken } from "../../utils/fetchFn";
import { BASE_URL } from "../../utils/constant";
import LoadingBlock from "../../components/LoadingBlock";
import CartGroupByRestaurant from "./CartGroupByRestaurant";

const Cart = () => {
  const {
    info: { accessToken },
  } = useGlobal();

  const { data } = useQuery({
    queryKey: ["carts"],
    queryFn: () =>
      fetchWithAccessToken(`${BASE_URL}/carts`, "GET", accessToken),
  });

  const groupedItems = groupByRestaurantId(data?.order_items);

  return (
    <div className="flex-1 flex flex-col items-center gap-3 min-w-max">
      <div className="w-full flex items-center gap-3 border border-neutral-300 rounded-xl p-2 px-4">
        <div className="w-full grid grid-cols-6 place-items-center gap-6 font-semibold text-sm">
          <div className="col-span-2 justify-self-start pl-12">
            <h2>Sản phẩm</h2>
          </div>
          <h2>Giá</h2>
          <h2>Số lượng</h2>
          <h2>Tổng</h2>
          <h2>Thao tác</h2>
        </div>
      </div>
      <LoadingBlock>
        {data?.order_items &&
          Object.keys(groupedItems).map((key) => {
            return (
              <CartGroupByRestaurant key={key} items={groupedItems[key]} />
            );
          })}
      </LoadingBlock>
    </div>
  );
};

function groupByRestaurantId(items) {
  if (!items) return {};
  const groupedItems = {};
  items.forEach((item) => {
    if (!groupedItems[item.restaurant_id]) {
      groupedItems[item.restaurant_id] = [];
    }
    groupedItems[item.restaurant_id].push(item);
  });
  return groupedItems;
}

export default Cart;
