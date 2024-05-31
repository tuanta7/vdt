import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useMutation } from "@tanstack/react-query";
import { toast } from "react-hot-toast";
import axios from "axios";

import LoadingButton from "../../components/LoadingButton";
import GoogleButton from "./GoogleButton";
import { BASE_URL } from "../../utils/constant";
import useGlobal from "../../hooks/useGlobal";

const RegisterForm = () => {
  const navigate = useNavigate();
  const { dispatch } = useGlobal();
  const { handleSubmit, register, reset, formState, getValues } = useForm();
  const { errors } = formState;

  const { mutate, isPending } = useMutation({
    mutationFn: (payload) =>
      axios
        .post(`${BASE_URL}/auth/register`, payload, {
          withCredentials: true,
        })
        .then((res) => res.data)
        .then((axiosData) => axiosData.data),
    onSuccess: (data) => {
      reset();
      toast.success("Đăng ký thành công");
      dispatch({ type: "SET_USER", payload: data.user });
      dispatch({ type: "SET_ACCESS_TOKEN", payload: data.access_token });
      navigate("/");
    },
  });

  const onSubmit = (payload) => {
    mutate(payload);
  };

  return (
    <div className="p-6 my-6 min-w-[400px] max-h-content border rounded-xl bg-base-100 ">
      <h2 className="flex items-center gap-2">
        <p className="text-2xl font-bold">Chào mừng đến</p>
        <img src="/text-logo.png" alt="logo" className="w-24" />
      </h2>
      <form
        className="mt-6"
        onSubmit={handleSubmit(onSubmit)}
        autoComplete="on"
      >
        <label className="form-control w-full">
          <div className="label">
            <span className="label-text font-semibold">Họ và tên</span>
            <p className="text-red-600 text-sm">{errors?.email?.message}</p>
          </div>
          <input
            type="full_name"
            className="input input-bordered"
            {...register("full_name", {
              required: "Họ và tên không được để trống",
            })}
          />
        </label>
        <label className="form-control w-full">
          <div className="label">
            <span className="label-text font-semibold">Email</span>
            <p className="text-red-600 text-sm">{errors?.email?.message}</p>
          </div>
          <input
            type="email"
            className="input input-bordered"
            {...register("email", {
              required: "Email không được để trống",
            })}
          />
        </label>
        <label className="form-control w-full">
          <div className="label">
            <span className="label-text font-semibold">Mật khẩu</span>
            <p className="text-red-600 text-sm">{errors?.password?.message}</p>
          </div>
          <input
            type="password"
            className=" input input-bordered"
            {...register("password", {
              required: "Mât khẩu không được để trống",
              minLength: {
                value: 8,
                message: "Mật khẩu phải có ít nhất 8 ký tự",
              },
            })}
          />
        </label>
        <label className="form-control w-full">
          <div className="label">
            <span className="label-text font-semibold">Nhập lại mật khẩu</span>
            <p className="text-red-600 text-sm">
              {errors?.confirm_password?.message}
            </p>
          </div>
          <input
            type="password"
            className=" input input-bordered"
            {...register("confirm_password", {
              validate: (value) =>
                value === getValues("password") || "Mật khẩu không khớp",
            })}
          />
        </label>
        <button
          type="submit"
          className="btn btn-primary w-full rounded-xl mt-4 text-base-100"
        >
          <LoadingButton isLoading={isPending}>Đăng ký tài khoản</LoadingButton>
        </button>
      </form>
      <p className="mt-3 text-center text-sm text-neutral">
        Bạn đã có tài khoản?{" "}
        <Link to="/auth/login" className="font-semibold text-primary">
          Đăng nhập
        </Link>
      </p>
      <div className="mb-6 border-b border-base-content-400 text-center">
        <div className="px-2 inline-block text-sm text-neutral bg-base-100 transform translate-y-1/2">
          Hoặc
        </div>
      </div>
      <GoogleButton />
    </div>
  );
};

export default RegisterForm;
