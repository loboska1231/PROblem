package org.copyria2.order_service.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ChangeDto(
       String ccy,
       String base_ccy,
       BigDecimal buy,
       BigDecimal sale
) { }
