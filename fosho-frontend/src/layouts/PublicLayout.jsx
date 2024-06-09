import { useQuery } from "@tanstack/react-query";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import Categories from "../features/public/Categories";
import Mapbox from "../features/public/maps/Mapbox";
import Avatar from "../features/users/Avatar";
import useGlobal from "../hooks/useGlobal";
import { Outlet } from "react-router-dom";

import { fetchWithAccessToken, fetchWithCredentials } from "../utils/fetchFn";
import { BASE_URL } from "../utils/constant";
import { useEffect } from "react";

const PublicLayout = () => {
  const { info, dispatch } = useGlobal();

  const { data, error } = useQuery({
    queryKey: ["info"],
    queryFn: () =>
      fetchWithAccessToken(`${BASE_URL}/auth/info`, "GET", info.accessToken),
    retry: 0,
    meta: { DisableGlobalErrorHandling: true },
  });

  useEffect(() => {
    if (data) {
      dispatch({ type: "SET_USER", payload: data.user });
    }
    if (error) {
      console.log("Try to refresh token");
      fetchWithCredentials(`${BASE_URL}/auth/refresh`, "POST")
        .then((data) => {
          dispatch({ type: "SET_USER", payload: data.user });
          dispatch({ type: "SET_ACCESS_TOKEN", payload: data.accessToken });
          console.log("Refreshed token successfully!!!");
          return data.access_token;
        })
        .catch((error) => {
          console.log("Error refreshing token: ", error);
          console.log("You will have to login again!");
        });
    }
  }, [data, dispatch, error]);

  const UserAvatar = info.user && <Avatar user={info.user} />;

  return (
    <div className="w-full h-screen bg-base-100 overflow-x-hidden">
      <Navbar UserAvatar={UserAvatar} />
      <div className="w-full p-3 flex justify-between gap-3 min-h-[80vh]">
        <Categories />
        <Outlet />
        <Mapbox />
      </div>
      <Footer />
    </div>
  );
};

export default PublicLayout;
