# Xây dựng hệ thống/ ứng dụng đặt đồ ăn quy trình logistic có sử dụng bản đồ

- Tìm hiểu về framework Spring boot, ReactJS
- Database mong muốn mariaDB
- TÌm hiểu Elastic Search để phục vụ tìm kiếm mặt hàng, đơn hàng,...
- Tìm hiểm về Debezium
- Tìm hiểu về caching dữ liệu: redis
- Tìm hiểu mapbox (GIS) có thể tích hợp bản đồ để tìm kiếm của hàng trên web
- Tìm hiểu về websocket để push notification
- Triển khai docker, k8s

## 1. Giao diện

- Trang chủ
- Đăng nhập (Oauth2)
- Theo dõi đơn hàng
- Lịch sử đơn hàng
- Thanh điều hướng: Tìm kiếm, Giỏ hàng, Thông báo trạng thái đơn hàng, Chat
- Thông tin cá nhân: Địa chỉ, Tên, Số điện thoại, Email,...
- Chi tiết món ăn
- Chi tiết nhà hàng

### Đặt đố

- Giỏ hàng
- Đặt hàng, thanh toán

## 2. API

## Người dùng

## Nhà hàng

- Doanh thu theo ngày/tuần/tháng/quý/năm

## Người giao hàng

- Vị trí trên bản đồ (real-time)
- Xác nhận giao thành công

## Thông báo Real-time:

- Thông báo có đơn hàng
- Thông báo trạng thái đơn hàng: Chờ xác nhận (đặt thành công), Đang làm, Đang giao, Giao thành công
