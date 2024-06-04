import { useQuery } from "@tanstack/react-query";

import useGlobal from "../../hooks/useGlobal";
import { fetchWithAccessToken } from "../../utils/fetchFn";
import { BASE_URL } from "../../utils/constant";
import LoadingBlock from "../../components/LoadingBlock";
import CartItem from "./CartItem";

const Cart = () => {
  const {
    info: { accessToken },
  } = useGlobal();

  const { data } = useQuery({
    queryKey: ["carts"],
    queryFn: () =>
      fetchWithAccessToken(`${BASE_URL}/carts`, "GET", accessToken),
  });

  return (
    <div className="flex flex-col gap-3">
      <LoadingBlock>
        {data?.order_items.map((item) => (
          <div key={item.id}>
            <CartItem item={item} />
          </div>
        ))}
      </LoadingBlock>
      <div>{groupByRestaurantId("")}</div>
    </div>
  );
};

function groupByRestaurantId(items) {
  return items;
}

export default Cart;
