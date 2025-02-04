package com.Raf.discoveryBooks.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MOD_READ("management:read"),
    MOD_UPDATE("management:update"),
    MOD_CREATE("management:create"),
    MOD_DELETE("management:delete");

    @Getter
    private final String permission;
}
