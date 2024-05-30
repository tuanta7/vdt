import PropTypes from "prop-types";
import { useQuery } from "@tanstack/react-query";

import { fetchPublicGet } from "../../utils/fetchFn";
import { BASE_URL } from "../../utils/constant";

const RestaurantList = ({ long, lat }) => {
  const { data, isLoading, error } = useQuery({
    queryKey: ["restaurants"],
    queryFn: () =>
      fetchPublicGet(`${BASE_URL}/restaurants?long=${long}&lat=${lat}`),
  });

  return <div className="flex-1 mx-10">{JSON.stringify(data)}</div>;
};
RestaurantList.propTypes = {
  long: PropTypes.number,
  lat: PropTypes.number,
};

export default RestaurantList;
