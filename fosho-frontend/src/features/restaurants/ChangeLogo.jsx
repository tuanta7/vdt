import PropTypes from "prop-types";
import { PencilSquareIcon } from "@heroicons/react/24/outline";
import { useMutation } from "@tanstack/react-query";
import { fetchWithAccessToken } from "../../utils/fetchFn";

import useGlobal from "../../hooks/useGlobal";
import { BASE_URL } from "../../utils/constant";

const ChangeLogo = ({ restaurantId }) => {
  const {
    info: { accessToken },
  } = useGlobal();

  const { mutate, isPending, error } = useMutation({
    mutationFn: (image) => {
      const formData = new FormData();
      formData.append("logoFile", image);

      return fetchWithAccessToken(
        `${BASE_URL}/restaurants/${restaurantId}/logo`,
        "PATCH",
        accessToken,
        formData
      );
    },
  });

  return (
    <form className="flex items-center justify-center">
      <label
        htmlFor="avatar-upload"
        className="btn btn-xs glass text-base-content max-w-fit"
      >
        {isPending ? "..." : <PencilSquareIcon className="w-4" />}
      </label>
      <input
        id="avatar-upload"
        name="avatar"
        type="file"
        className="hidden"
        accept="image/*"
        onChange={(e) => {
          console.log(e.target.files[0]);
          if (e.target.files[0]) {
            mutate(e.target.files[0]);
          }
        }}
      />
    </form>
  );
};
ChangeLogo.propTypes = {
  restaurantId: PropTypes.number,
};

export default ChangeLogo;
