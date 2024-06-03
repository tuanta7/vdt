import { useParams } from "react-router-dom";

const UserDishList = () => {
  const { restaurantId } = useParams();

  return <div>Dishes, ID: {restaurantId}</div>;
};

export default UserDishList;
