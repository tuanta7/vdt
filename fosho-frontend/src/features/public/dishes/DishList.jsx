import { useState } from "react";
import { useQuery } from "@tanstack/react-query";

import { fetchPublicGet } from "../../../utils/fetchFn";
import { BASE_URL } from "../../../utils/constant";
import LoadingBlock from "../../../components/LoadingBlock";
import DishItem from "../../../components/DishItem";
import AddToCartBar from "../../cart/AddToCartBar";
import Pagination from "../../../components/Pagination";

const DishList = () => {
  const [page, setPage] = useState(1);
  const { isPending, data, error } = useQuery({
    queryKey: ["dishes", page],
    queryFn: () =>
      fetchPublicGet(`${BASE_URL}/dishes?limit=8&page=${page}`, "GET"),
  });

  return (
    <div className="flex-1">
      <div className="flex flex-wrap justify-center items-center gap-2 mb-6 mt-2">
        <p className="text-sm font-semibold">🎯Bộ lọc</p>
        <select
          className="select select-bordered select-sm  max-w-xs"
          defaultValue={0}
        >
          <option value={0} disabled>
            Sắp xếp theo
          </option>
          <option>Bán chạy nhất</option>
          <option>Đánh giá cao nhất</option>
          <option>Vị trí gần nhất</option>
        </select>
        <select
          className="select select-bordered select-sm  max-w-xs"
          defaultValue={0}
        >
          <option value={0} disabled>
            Tỉnh/Thành phố
          </option>
          <option>Hà Nội</option>
          <option>TP Hồ Chí Minh</option>
          <option>Đà Nẵng</option>
        </select>
      </div>
      <div className="flex flex-wrap justify-evenly gap-6 mb-10">
        <LoadingBlock isLoading={isPending} error={error}>
          {data?.dishes?.map((d) => (
            <DishItem
              key={d.id}
              dish={d}
              buttonBar={<AddToCartBar dishId={d.id} />}
            />
          ))}
        </LoadingBlock>
      </div>
      <Pagination current={page} setFn={setPage} total={data?.total} />
    </div>
  );
};

export default DishList;
