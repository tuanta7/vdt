import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { useSearchParams } from "react-router-dom";

import { fetchPublicGet } from "../../../utils/fetchFn";
import { BASE_URL } from "../../../utils/constant";
import LoadingBlock from "../../../components/LoadingBlock";
import DishItem from "../../../components/DishItem";
import AddToCartBar from "../../cart/AddToCartBar";
import Pagination from "../../../components/Pagination";
import { XMarkIcon } from "@heroicons/react/24/outline";

const DishList = () => {
  const [searchParams] = useSearchParams();
  const search = searchParams.get("q") || "";

  const [page, setPage] = useState(1);
  const { isPending, data, error } = useQuery({
    queryKey: ["dishes", page, search],
    queryFn: () =>
      fetchPublicGet(
        `${BASE_URL}/dishes?limit=8&page=${page}&q=${search}`,
        "GET"
      ),
  });

  return (
    <div className="flex-1">
      <div className="flex flex-wrap justify-center items-center gap-2 mb-6">
        <p className="text-sm font-semibold">🎯Bộ lọc</p>
        <select
          className="select select-bordered select-sm max-w-xs"
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
        {search && (
          <div className="flex items-center gap-2 ml-12">
            <p className="text-sm font-semibold">
              Kết quả tìm kiếm cho:{" "}
              <span className="text-primary font-semibold">
                &quot;{search}&quot;
              </span>
            </p>
            <button
              className="btn btn-xs btn-ghost btn-circle text-primary p-0 -mb-1"
              onClick={() => {
                searchParams.delete("q");
                window.history.replaceState({}, "", window.location.pathname);
                window.location.reload();
              }}
            >
              <XMarkIcon className="w-3 h-3" />
            </button>
          </div>
        )}
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
