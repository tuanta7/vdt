package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdt.fosho.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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


}
