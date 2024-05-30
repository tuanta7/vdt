import PropTypes from "prop-types";
const Avatar = ({ user }) => {
  if (!user.avatar_url) {
    return (
      <div className="avatar placeholder px-2">
        <div className="bg-white text-neutral-content rounded-full ring-2 ring-secondary w-6 ring-offset-base-100 ring-offset-2">
          <span className="text-lg">
            {user.full_name.charAt(0).toUpperCase()}
          </span>
        </div>
      </div>
    );
  }
  return (
    <div className="avatar online">
      <div className="w-8 rounded-full">
        <img src={user.avatar_url} />
      </div>
    </div>
  );
};
Avatar.propTypes = {
  user: PropTypes.object.isRequired,
};

export default Avatar;
