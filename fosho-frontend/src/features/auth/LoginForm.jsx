import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useMutation } from "@tanstack/react-query";
import { toast } from "react-hot-toast";
import axios from "axios";

import LoadingButton from "../../components/LoadingButton";
import GoogleButton from "./GoogleButton";
import { BASE_URL } from "../../utils/constant";
import useGlobal from "../../hooks/useGlobal";

const LoginForm = () => {
  const navigate = useNavigate();
  const { dispatch } = useGlobal();
  const { handleSubmit, register, reset, formState } = useForm();
  const { errors } = formState;

  const { mutate, isPending } = useMutation({
    mutationFn: (payload) =>
      axios
        .post(`${BASE_URL}/auth/login`, payload)
        .then((res) => res.data)
        .then((axiosData) => axiosData.data),
    onSuccess: (data) => {
      reset();
      toast.success("Đăng nhập thành công");
      dispatch({ type: "SET_USER", payload: data.user });
      dispatch({ type: "SET_ACCESS_TOKEN", payload: data.accessToken });
      navigate("/");
    },
  });

  const onSubmit = (payload) => {
    mutate(payload);
  };

  return (
    <div className="p-6 my-6 min-w-[400px] max-h-[500px] border rounded-xl bg-base-100 ">
      <h2 className="flex items-center gap-2">
        <p className="text-2xl font-bold ">Đăng nhập vào</p>
        <img src="/text-logo.png" alt="logo" className="w-24" />
      </h2>
      <form
        className="mt-6"
        onSubmit={handleSubmit(onSubmit)}
        autoComplete="on"
      >
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
          <div className="label">
            <div className="text-sm">
              <Link to="forgot" className="font-semibold text-primary">
                Quên mật khẩu?
              </Link>
            </div>
          </div>
        </label>
        <button
          type="submit"
          className="btn btn-primary w-full rounded-xl mt-4 text-base-100"
        >
          <LoadingButton isLoading={isPending}>Đăng nhập</LoadingButton>
        </button>
      </form>
      <p className="mt-3 text-center text-sm text-neutral">
        Bạn chưa có tài khoản?{" "}
        <Link to="/auth/register" className="font-semibold text-primary">
          Đăng ký ngay
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
LoginForm.propTypes = {};

export default LoginForm;
