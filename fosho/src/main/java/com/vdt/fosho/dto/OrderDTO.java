package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vdt.fosho.entity.OrderItem;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Order can not be empty")
    private List<OrderItem> items;
}
