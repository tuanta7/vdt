import PropTypes from "prop-types";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import { MAPBOX_ACCESS_TOKEN } from "../../../utils/constant";

const Address = ({ long, lat }) => {
  const { data: address } = useQuery({
    queryKey: ["reverse-geocode"],
    queryFn: async () =>
      axios
        .get(
          `https://api.mapbox.com/search/geocode/v6/reverse?longitude=${long}&latitude=${lat}&access_token=${MAPBOX_ACCESS_TOKEN}`
        )
        .then((res) => res?.data?.features[0]?.properties?.full_address),
  });
  return <span> {address || "Đang tìm vị trí của bạn 🛫🏝️"}</span>;
};
Address.propTypes = {
  long: PropTypes.number,
  lat: PropTypes.number,
};

export default Address;
