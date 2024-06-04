import PropTypes from "prop-types";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useForm } from "react-hook-form";

import LoadingButton from "../../components/LoadingButton";
import { fetchWithAccessToken } from "../../utils/fetchFn";
import { BASE_URL } from "../../utils/constant";
import useGlobal from "../../hooks/useGlobal";

const ShippingAddressCreate = ({ cancel }) => {
  const queryClient = useQueryClient();
  const {
    info: { accessToken },
  } = useGlobal();

  const { mutate, isPending } = useMutation({
    mutationFn: (payload) =>
      fetchWithAccessToken(
        `${BASE_URL}/addresses`,
        "POST",
        accessToken,
        payload
      ),
    onSuccess: () => {
      queryClient.invalidateQueries(["info"]);
      cancel();
    },
  });

  const { register, handleSubmit, errors } = useForm();

  const onSubmit = (payload) => {
    mutate(payload);
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="p-3 mb-3 border border-neutral-400 rounded-xl"
    >
      <h2 className="text-primary text-sm font-semibold mb-2 pl-1">
        Thêm địa chỉ giao hàng mới
      </h2>
      <label className="form-control w-full">
        <div className="label">
          <span className="label-text">Tên gợi nhớ</span>
          <p className="text-red-600 text-sm">{errors?.name?.message}</p>
        </div>
        <input
          type="text"
          className="input input-sm input-bordered"
          {...register("name", {
            required: "Tên không được để trống",
          })}
        />
      </label>
      <label className="form-control w-full">
        <div className="label">
          <span className="label-text">Địa chỉ chi tiết</span>
          <p className="text-red-600 text-sm">{errors?.address?.message}</p>
        </div>
        <input
          type="text"
          className="input input-sm input-bordered"
          {...register("address", {
            required: "Địa chỉ không được để trống",
          })}
        />
      </label>
      <div className="flex flex-row-reverse gap-3 mt-4">
        <button
          className="btn btn-primary text-base-100 rounded-lg btn-sm"
          type="submit"
          disabled={isPending}
        >
          <LoadingButton isLoading={isPending}>Xác nhận</LoadingButton>
        </button>
        <button
          className="btn btn-ghost rounded-lg btn-sm"
          type="reset"
          onClick={cancel}
        >
          Hủy
        </button>
      </div>
    </form>
  );
};

ShippingAddressCreate.propTypes = {
  cancel: PropTypes.func.isRequired,
};

export default ShippingAddressCreate;
