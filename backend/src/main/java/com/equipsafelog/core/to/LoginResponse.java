package com.equipsafelog.core.to;

import com.equipsafelog.core.domain.UserRole;

public record LoginResponse(String token, UserRole role) {

}
