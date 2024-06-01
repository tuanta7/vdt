import { useParams } from "react-router-dom";
import useGlobal from "../../hooks/useGlobal";
import { useQuery } from "@tanstack/react-query";
import { fetchWithAccessToken } from "../../utils/fetchFn";

import { BASE_URL } from "../../utils/constant";

const UserRestaurantList = () => {
  const { info } = useGlobal();
  const { userId } = useParams();

  const { isPending, data, error } = useQuery({
    queryKey: ["userRestaurants", userId],
    queryFn: () =>
      fetchWithAccessToken(
        `${BASE_URL}/users/${userId}/restaurants`,
        "GET",
        info.accessToken
      ),
  });

  return (
    <div>
      <h1>{JSON.stringify(data)}</h1>
    </div>
  );
};

export default UserRestaurantList;
