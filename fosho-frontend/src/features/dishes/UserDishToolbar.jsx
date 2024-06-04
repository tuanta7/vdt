import PropTypes from "prop-types";
import { useParams } from "react-router-dom";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import useGlobal from "../../hooks/useGlobal";
import { PencilSquareIcon } from "@heroicons/react/24/outline";
import { toast } from "react-hot-toast";
import { fetchWithAccessToken } from "../../utils/fetchFn";
import { BASE_URL } from "../../utils/constant";

const UserDishToolbar = ({ dishId }) => {
  const queryClient = useQueryClient();
  const { userId, restaurantId } = useParams();
  const {
    info: { accessToken },
  } = useGlobal();

  const { mutate, isPending } = useMutation({
    mutationFn: (image) => {
      const formData = new FormData();
      formData.append("image", image);

      return fetchWithAccessToken(
        `${BASE_URL}/restaurants/${restaurantId}/dishes/${dishId}/thumbnail`,
        "PATCH",
        accessToken,
        formData
      );
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["user-dishes", userId, restaurantId]);
      toast.success("Cập nhật thành công");
    },
  });

  return (
    <form className="flex items-center justify-center">
      <label
        htmlFor={`thumbnail-upload-${dishId}`}
        className="btn btn-xs text-neutral-500 max-w-fit"
      >
        {isPending ? (
          <span className="loading loading-spinner text-primary loading-xs" />
        ) : (
          <PencilSquareIcon className="w-4" />
        )}
      </label>
      <input
        id={`thumbnail-upload-${dishId}`}
        name={`thumbnail-${dishId}`}
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

UserDishToolbar.propTypes = {
  dishId: PropTypes.number.isRequired,
};

export default UserDishToolbar;
