import useGlobal from "../../hooks/useGlobal";
import Mapbox from "../public/maps/Mapbox";

const Profile = () => {
  const { info } = useGlobal();
  const { user } = info;

  return (
    <div className="flex-1 flex justify-between">
      <div className="flex gap-3">
        <div className="avatar">
          <div className="w-44 h-44 rounded-xl">
            <img src={user.avarta_url || "/default.svg"} alt="avatar" />
          </div>
        </div>
        <div className="flex flex-col gap-1">
          <p className="font-semibold">{user.full_name}</p>
          <p className="">{user.email}</p>
          <p>
            {user.phone || (
              <button className="btn btn-xs max-w-fit">
                Thêm số điện thoại
              </button>
            )}
          </p>
          <button className="btn btn-xs max-w-fit">Tạo ảnh đại diện mới</button>
          <button className="btn btn-xs max-w-fit">Đổi mật khẩu</button>
        </div>
      </div>
      <Mapbox />
    </div>
  );
};
Profile.propTypes = {};

export default Profile;
