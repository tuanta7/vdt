import PropTypes from "prop-types";
import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

import useGlobal from "../../hooks/useGlobal";
import { BASE_URL } from "../../utils/constant";
import { fetchWithAccessToken } from "../../utils/fetchFn";

const OrderConfirm = ({ items, restaurantId }) => {
  const navigate = useNavigate();
  const {
    info: { user, accessToken },
  } = useGlobal();

  const { mutate } = useMutation({
    mutationFn: () =>
      fetchWithAccessToken(
        `${BASE_URL}/restaurants/${restaurantId}/orders`,
        "POST",
        accessToken,
        items
      ),
    onSuccess: (data) => {
      navigate(`/users//${user.id}orders/${data.order.id}`);
    },
  });

  return <div></div>;
};

OrderConfirm.propTypes = {};

export default OrderConfirm;
