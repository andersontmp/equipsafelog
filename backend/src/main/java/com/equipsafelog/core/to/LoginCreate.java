package com.equipsafelog.core.to;

import com.equipsafelog.core.domain.UserRole;

public record LoginCreate(String login, String password, UserRole role) {

}
