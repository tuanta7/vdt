import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Toaster } from "react-hot-toast";
import Page404 from "./components/Page404";
import AuthLayout from "./layouts/AuthLayout";
import LoginForm from "./features/auth/LoginForm";
import RegisterForm from "./features/auth/RegisterForm";
import PublicLayout from "./layouts/PublicLayout";
import RestaurantList from "./features/public/restaurants/RestaurantList";
import DishList from "./features/public/dishes/DishList";

import Profile from "./features/users/Profile";
import UserRestaurantList from "./features/restaurants/UserRestaurantList";
import ProtectedLayout from "./layouts/ProtectedLayout";
import RestaurantDetail from "./features/restaurants/RestaurantDetail";
import DishDetail from "./features/dishes/DishDetail";
import Cart from "./features/cart/Cart";
import OrderTracking from "./features/orders/OrderTracking";

function App() {
  return (
    <BrowserRouter>
      <Toaster
        position="top-center"
        gutter={12}
        containerStyle={{ margin: "8px", padding: "2px" }}
        toastOptions={{
          success: {
            duration: 3000,
          },
          error: {
            duration: 5000,
          },
          style: {
            fontSize: "16px",
            width: "max-content",
          },
        }}
      />
      <Routes>
        <Route path="/" element={<PublicLayout />}>
          <Route index element={<RestaurantList />} />
          <Route path="restaurants" element={<RestaurantList />} />
          <Route path="dishes" element={<DishList />} />
        </Route>
        <Route path="/users/:userId" element={<ProtectedLayout />}>
          <Route index element={<Profile />} />
          <Route path="cart" element={<Cart />} />
          <Route path="orders" element={<OrderTracking />} />
          <Route path="info" element={<Profile />} />
          <Route path="restaurants" element={<UserRestaurantList />} />

          <Route
            path="restaurants/:restaurantId"
            element={<RestaurantDetail />}
          />
          <Route
            path="restaurants/:restaurantId/dishes/:dishId"
            element={<DishDetail />}
          />
        </Route>
        <Route path="/auth" element={<AuthLayout />}>
          <Route index element={<LoginForm />} />
          <Route path="login" element={<LoginForm />} />
          <Route path="register" element={<RegisterForm />} />
        </Route>
        <Route path="*" element={<Page404 />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
