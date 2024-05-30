import Navbar from "../components/Navbar";
import Mapbox from "../features/public/maps/Mapbox";
import RestaurantList from "../features/public/RestaurantList";
import Avatar from "../features/users/profile/Avatar";
import useGlobal from "../hooks/useGlobal";

const Homepage = () => {
  const { info } = useGlobal();
  console.log(info);

  const UserAvatar = info.user && <Avatar user={info.user} />;

  return (
    <div className="w-full h-screen bg-base-100 ">
      <Navbar UserAvatar={UserAvatar} />
      <div className="w-full p-6 flex justify-between gap-3">
        <div>Categories</div>
        <RestaurantList />
        <Mapbox />
      </div>
    </div>
  );
};

export default Homepage;
