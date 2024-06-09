package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.EnumNaming;
import com.vdt.fosho.entity.OrderItem;
import com.vdt.fosho.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private Long id;

    private List<OrderItemDTO> items;

    private String status;

    @JsonProperty("total_price")
    private double totalPrice;

    @JsonProperty("total_discount")
    private double totalDiscount;

    @JsonProperty("shipping_fee")
    private double shippingFee;

    @JsonProperty("shipping_address")
    private ShippingAddressDTO shippingAddress;

    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    // For creating order
    @JsonProperty("order_item_ids")
    private List<Long> itemIds;

    @JsonProperty("shipping_address_id")
    private Long shippingAddressId;
}
