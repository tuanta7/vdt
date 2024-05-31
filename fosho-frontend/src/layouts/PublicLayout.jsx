import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import Categories from "../features/public/Categories";
import Mapbox from "../features/public/maps/Mapbox";
import Avatar from "../features/users/profile/Avatar";
import useGlobal from "../hooks/useGlobal";
import { Outlet } from "react-router-dom";

const PublicLayout = () => {
  const { info } = useGlobal();
  console.log(info);

  const UserAvatar = info.user && <Avatar user={info.user} />;

  return (
    <div className="w-full h-screen bg-base-100 overflow-x-hidden">
      <Navbar UserAvatar={UserAvatar} />
      <div className="w-full py-6 pr-3 flex justify-between gap-3">
        <Categories />
        <Outlet />
        <div className="max-sm:hidden">
          <Mapbox />
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default PublicLayout;
