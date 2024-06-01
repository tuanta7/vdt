import useGlobal from "../../hooks/useGlobal";
import Mapbox from "../public/maps/Mapbox";

const Profile = () => {
  const { info } = useGlobal();
  const { user } = info;

  const shippingAddress =
    user?.shipping_addresses?.length > 0 ? (
      user?.shipping_addresses?.map((s) => (
        <p key={s.address} className="pl-1">
          📍 {s.address}
        </p>
      ))
    ) : (
      <button className="btn btn-xs max-w-fit">
        Thêm địa chỉ giao hàng 🏠
      </button>
    );

  const phone = user?.phone ? (
    <p className="pl-1">{user?.phone}</p>
  ) : (
    <button className="btn btn-xs max-w-fit">Thêm số điện thoại 📱</button>
  );

  return (
    <div className="flex-1 flex justify-between">
      <div className="flex gap-3">
        <div className="avatar flex flex-col">
          <div className="w-44 h-44 rounded-xl">
            <img src={user?.avarta_url || "/default.svg"} alt="avatar" />
          </div>
          <button className="btn btn-xs btn-primary glass text-base-100 max-w-fit z-1 -mt-44">
            Thay ảnh
          </button>
        </div>
        <div className="h-fit flex flex-col gap-2">
          <div className="pl-1">
            <h2 className="font-semibold ">{user?.full_name}</h2>
            <p className=" text-sm">{user?.email}</p>
          </div>
          {phone}
          {shippingAddress}
        </div>
      </div>

      <Mapbox />
    </div>
  );
};
Profile.propTypes = {};

export default Profile;
