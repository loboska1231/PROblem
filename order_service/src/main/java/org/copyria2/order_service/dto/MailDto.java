package org.copyria2.order_service.dto;

import lombok.Builder;

@Builder
public record MailDto(
        String from,
        String to,
        String title,
        String message
) {
}
