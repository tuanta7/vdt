import { useQuery } from "@tanstack/react-query";
import { useState } from "react";

import useGlobal from "../../hooks/useGlobal";
import { fetchWithAccessToken } from "../../utils/fetchFn";
import { BASE_URL } from "../../utils/constant";
import LoadingBlock from "../../components/LoadingBlock";
import CartItem from "./CartItem";
import TotalBar from "./TotalBar";

const Cart = () => {
  const [checkAll, setCheckAll] = useState(false);
  const [total, setTotal] = useState(0);

  const {
    info: { accessToken },
  } = useGlobal();

  const { data } = useQuery({
    queryKey: ["carts"],
    queryFn: () =>
      fetchWithAccessToken(`${BASE_URL}/carts`, "GET", accessToken),
  });

  return (
    <div className="flex-1 flex flex-col items-center gap-3 min-w-max">
      <div className="w-full flex items-center gap-3 border border-neutral-300 rounded-xl p-2">
        <input
          type="checkbox"
          className="checkbox checkbox-md checkbox-info"
          checked={checkAll}
          onChange={(e) => {
            if (e.target.checked) {
              setCheckAll(true);
              setTotal(
                data?.order_items?.reduce(
                  (acc, item) =>
                    acc +
                    item.quantity * (item.dish.price - item.dish.discount),
                  0
                )
              );
            } else {
              setTotal(0);
              setCheckAll(false);
            }
            Array.from(document.getElementsByClassName("checkbox")).forEach(
              (checkbox) => {
                checkbox.checked = e.target.checked;
              }
            );
          }}
        />
        <div className="w-full grid grid-cols-6 place-items-center gap-6 font-semibold text-sm">
          <div className="flex gap-3 col-span-2 justify-self-start">
            <h2>Sản phẩm</h2>
          </div>
          <h2>Giá</h2>
          <h2>Số lượng</h2>
          <h2>Tổng</h2>
          <h2>Thao tác</h2>
        </div>
      </div>
      <LoadingBlock>
        {data?.order_items.map((item) => (
          <div
            key={item.id}
            className="w-full flex items-center gap-3 border border-neutral-300 p-2 rounded-xl"
          >
            <input
              type="checkbox"
              className="checkbox checkbox-md checkbox-info"
              value={item.id}
              onChange={(e) => {
                const money =
                  item.quantity * (item.dish.price - item.dish.discount);
                if (e.target.checked) {
                  setTotal(total + money);
                } else {
                  setCheckAll(false);
                  setTotal(total - money);
                }
              }}
            />
            <CartItem item={item} />
          </div>
        ))}
      </LoadingBlock>
      <div>{groupByRestaurantId("")}</div>
      <TotalBar total={total} />
    </div>
  );
};

function groupByRestaurantId(items) {
  return items;
}

export default Cart;
