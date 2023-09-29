package com.bookingService.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    ROLE_ADMIN,
    ROLE_USER
}
