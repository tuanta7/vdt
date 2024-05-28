import Navbar from "../components/Navbar";
import GoogleButton from "../features/auth/GoogleButton";

const Homepage = () => {
  return (
    <div className="w-full h-screen bg-base-100">
      <Navbar LoginButton={<GoogleButton />} />
    </div>
  );
};

export default Homepage;
