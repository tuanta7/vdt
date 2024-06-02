import { useMutation } from "@tanstack/react-query";
import { useForm } from "react-hook-form";

const ShippingAddressCreate = () => {
  const { mutate } = useMutation({});

  const { register, handleSubmit, errors } = useForm();

  const onSubmit = (payload) => {
    mutate(payload);
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="p-3 mb-3 border border-neutral-400 rounded-xl"
    >
      <h2 className="text-sm font-semibold mb-2 pl-1">
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
      <button className="btn btn-sm mt-3">Lưu</button>
    </form>
  );
};
ShippingAddressCreate.propTypes = {};

export default ShippingAddressCreate;
