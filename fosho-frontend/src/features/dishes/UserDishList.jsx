import { useParams } from "react-router-dom";
import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";
import { useQuery } from "@tanstack/react-query";

import { BASE_URL } from "../../utils/constant";
import { fetchPublicGet } from "../../utils/fetchFn";
import LoadingBlock from "../../components/LoadingBlock";
import DishItem from "../../components/DishItem";
import UserDishToolbar from "./UserDishToolbar";

const UserDishList = () => {
  const { restaurantId, userId } = useParams();

  const { isPending, data, error } = useQuery({
    queryKey: ["user-dishes", userId, restaurantId],
    queryFn: () =>
      fetchPublicGet(`${BASE_URL}/restaurants/${restaurantId}/dishes`, "GET"),
  });

  const search = (
    <div className="flex items-center justify-between border rounded-lg bg-base-100 w-full">
      <input
        type="text"
        placeholder="Tìm món ăn"
        className="input input-sm rounded-lg focus:border-none no-focus w-full"
      />
      <button className="btn btn-ghost rounded-lg btn-sm">
        <MagnifyingGlassIcon className="w-4 h-4" />
      </button>
    </div>
  );

  return (
    <div className="mt-6 border border-base-200 p-3 rounded-xl">
      <div className="flex flex-wrap justify-between items-center gap-3 mb-2">
        <h2 className="font-semibold text-lg text-primary pl-2">Thực đơn 🍽️</h2>
        <div className="flex gap-3">
          {search}
          <button className="btn btn-sm btn-primary text-base-100">
            Thêm món mới
          </button>
        </div>
      </div>
      <LoadingBlock number={3} isPending={isPending} error={error}>
        <div className="p-2">
          {data?.dishes?.map((d) => (
            <DishItem
              key={d.id}
              dish={d}
              buttonBar={<UserDishToolbar dishId={d.id} />}
            />
          ))}
        </div>
      </LoadingBlock>
    </div>
  );
};

export default UserDishList;
