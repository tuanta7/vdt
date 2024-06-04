import { useMutation } from "@tanstack/react-query";
import { useForm } from "react-hook-form";
import PropTypes from "prop-types";

const DishCreate = ({ cancel }) => {
  const { mutate } = useMutation();

  const { handleSubmit } = useForm();

  const onSubmit = (data) => {
    mutate(data);
  };

  return (
    <form
      className="p-3 mb-3 border border-neutral-400 rounded-xl"
      onSubmit={handleSubmit(onSubmit)}
      autoComplete="on"
    ></form>
  );
};
DishCreate.propTypes = {
  cancel: PropTypes.func.isRequired,
};

export default DishCreate;
