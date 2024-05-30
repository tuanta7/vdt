import Navbar from "../components/Navbar";
import GoogleButton from "../features/auth/GoogleButton";
import Mapbox from "../features/public/maps/Mapbox";
import RestaurantList from "../features/public/RestaurantList";

const Homepage = () => {
  return (
    <div className="w-full h-screen bg-base-100 ">
      <Navbar LoginButton={<GoogleButton />} />
      <div className="w-full p-6 flex justify-between gap-3">
        <div>Categories</div>
        <RestaurantList />
        {/* <Mapbox /> */}
      </div>
    </div>
  );
};

export default Homepage;
