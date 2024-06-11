import Order from "./Order";
import { useOutletContext } from "react-router-dom";

const OrderListPending = () => {
  const { orders } = useOutletContext();
  return orders?.map((o) => <Order key={o.id} order={o} />);
};

export default OrderListPending;
