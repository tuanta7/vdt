import { useState } from "react";
import useGlobal from "../../hooks/useGlobal";
import Mapbox from "../public/maps/Mapbox";
import ChangeAvatar from "./ChangeAvatar";
import ShippingAddressDelete from "./ShippingAddressDelete";
import ShippingAddressCreate from "./ShippingAddressCreate";
import { Link } from "react-router-dom";

const Profile = () => {
  const [addressForm, setAddressForm] = useState(false);
  const [phoneForm, setPhoneForm] = useState(false);

  const { info } = useGlobal();
  const { user } = info;

  const shippingAddress = user?.shipping_addresses?.map((s) => (
    <div
      key={s.address}
      className="flex items-center gap-6 justify-between p-2 mb-3 border border-neutral-500 hover:border-primary hover:cursor-pointer rounded-lg"
    >
      <div className="w-64">
        <h2 className="font-semibold">🏠 {s.name}</h2>
        <p className="break-words text-sm">🗺️ {s.address}</p>
      </div>
      <ShippingAddressDelete />
    </div>
  ));
  const phone = user?.phone ? (
    <p className="pl-1">{user?.phone}</p>
  ) : (
    <button
      className="btn btn-xs max-w-fit"
      onClick={() => setPhoneForm(!phoneForm)}
    >
      Thêm số điện thoại 📱
    </button>
  );

  return (
    <div className="flex-1 flex flex-wrap gap-8">
      <div className="flex flex-col gap-3">
        <div className="flex gap-3">
          <div className="avatar flex flex-col items-end">
            <div className="w-44 h-44 rounded-xl">
              <img src={user?.avarta_url || "/default.svg"} alt="avatar" />
            </div>
            <div className="-mt-44 mb-36">
              <ChangeAvatar userId={user?.id} />
            </div>
          </div>
          <div className="h-fit flex flex-col gap-2">
            <div className="pl-1">
              <h2 className="font-semibold ">{user?.full_name}</h2>
              <p className=" text-sm">{user?.email}</p>
            </div>
            {phone}
            <button
              className="btn btn-xs max-w-fit min-w-max"
              onClick={() => setAddressForm(!addressForm)}
            >
              Thêm địa chỉ giao hàng 🏠
            </button>
            <Link className="btn btn-xs max-w-fit">Đổi mật khẩu 🔑</Link>
          </div>
        </div>
        <div>
          <h2 className="font-semibold mb-2 pl-1 text-sm">Địa chỉ giao hàng</h2>
          {addressForm && (
            <ShippingAddressCreate cancel={() => setAddressForm(false)} />
          )}
          {user?.shipping_addresses?.length > 0 ? (
            shippingAddress
          ) : (
            <p className="text-neutral-400 text-sm">
              Bạn chưa thêm địa chỉ giao hàng nào
            </p>
          )}
        </div>
      </div>
      <div className="flex-1 border border-neutral-400 p-3 rounded-xl">
        FoshoFeed Coming soon...
      </div>
      <div className="max-lg:hidden">
        <Mapbox />
      </div>
    </div>
  );
};
Profile.propTypes = {};

export default Profile;
