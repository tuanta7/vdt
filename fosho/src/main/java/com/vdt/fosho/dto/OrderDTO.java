package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.EnumNaming;
import com.vdt.fosho.entity.OrderItem;
import com.vdt.fosho.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private Long id;

    private List<OrderItem> items;

    private String status;

    @JsonProperty("total_price")
    private double totalPrice;

    @JsonProperty("total_discount")
    private double totalDiscount;

    @JsonProperty("shipping_fee")
    private double shippingFee;

    @JsonProperty("payment_method")
    @NotBlank(message = "Payment method is required")
    @Pattern(regexp = "^(COD|VIETTEL_PAYGATE)$",
            message = "Payment method must be either COD or VIETTEL_PAYGATE"
    )
    private PaymentMethod paymentMethod;

    @JsonProperty("shipping_address")
    private ShippingAddressDTO shippingAddress;
}
