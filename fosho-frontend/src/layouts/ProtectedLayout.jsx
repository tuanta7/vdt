import { useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import PropTypes from "prop-types";
import { NavLink } from "react-router-dom";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import Avatar from "../features/users/Avatar";
import useGlobal from "../hooks/useGlobal";
import { Outlet } from "react-router-dom";

import { fetchWithAccessToken, fetchWithCredentials } from "../utils/fetchFn";
import { BASE_URL } from "../utils/constant";
import { useEffect } from "react";

const ProtectedLayout = () => {
  const navigate = useNavigate();
  const { info, dispatch } = useGlobal();

  const { data, error, refetch } = useQuery({
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
          navigate("/login");
        });
    }
  }, [data, dispatch, error, refetch, navigate]);

  const UserAvatar = info.user && <Avatar user={info.user} />;

  return (
    <div className="w-full h-screen bg-base-100 overflow-x-hidden">
      <Navbar UserAvatar={UserAvatar} />
      <div className="w-full py-6 px-3 flex gap-6 min-h-[80vh]">
        <div className="min-w-fit">
          <ul className="menu gap-2 bg-base-200 rounded-lg pr-2">
            <li>
              <NavLink
                to={`/users/${info?.user?.id}/info`}
                className="rounded-lg"
              >
                🕵🏼 <p className="max-sm:hidden"> Cá nhân</p>
              </NavLink>
            </li>
            <li>
              <NavLink
                to={`/users/${info?.user?.id}/restaurants`}
                className="rounded-lg"
              >
                🛎️ <p className="max-sm:hidden"> Cửa hàng</p>
              </NavLink>
            </li>
          </ul>
        </div>
        <Outlet />
      </div>
      <Footer />
    </div>
  );
};
ProtectedLayout.propTypes = {
  children: PropTypes.any,
};

export default ProtectedLayout;
