import PropTypes from "prop-types";
import { useQuery } from "@tanstack/react-query";
import { useSearchParams } from "react-router-dom";
import { fetchPublicGet } from "../../../utils/fetchFn";
import { BASE_URL } from "../../../utils/constant";
import LoadingBlock from "../../../components/LoadingBlock";
import RestaurantItem from "./RestaurantItem";

const RestaurantList = ({ long, lat }) => {
  const [searchParams] = useSearchParams();
  const search = searchParams.get("q") || "";

  const { data, isLoading, error } = useQuery({
    queryKey: ["restaurants", search],
    queryFn: () =>
      fetchPublicGet(
        `${BASE_URL}/restaurants?long=${long}&lat=${lat}&q=${search}`
      ),
  });

  const list = (
    <LoadingBlock isLoading={isLoading} error={error} vertical={false}>
      {data?.restaurants?.map((r) => (
        <RestaurantItem key={r.id} restaurant={r} />
      ))}
    </LoadingBlock>
  );

  return (
    <div className="flex-1">
      <div className="flex flex-wrap justify-center items-center gap-2 mb-6">
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
      <div className="flex flex-wrap justify-evenly gap-6">{list}</div>
    </div>
  );
};
RestaurantList.propTypes = {
  long: PropTypes.number,
  lat: PropTypes.number,
};

export default RestaurantList;
