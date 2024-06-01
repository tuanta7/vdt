import { useParams } from "react-router-dom";
import useGlobal from "../../hooks/useGlobal";
import { useQuery } from "@tanstack/react-query";
import LoadingBlock from "../../components/LoadingBlock";
import { fetchWithAccessToken } from "../../utils/fetchFn";

import { BASE_URL } from "../../utils/constant";
import UserRestaurantItem from "./UserRestaurantItem";

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

  const content = data?.restaurants?.map((restaurant) => (
    <UserRestaurantItem key={restaurant.id} restaurant={restaurant} />
  ));

  return (
    <LoadingBlock
      number={1}
      vertical={true}
      isLoading={isPending}
      error={error}
    >
      <div className="flex flex-col gap-6 w-full">{content}</div>
    </LoadingBlock>
  );
};

export default UserRestaurantList;
