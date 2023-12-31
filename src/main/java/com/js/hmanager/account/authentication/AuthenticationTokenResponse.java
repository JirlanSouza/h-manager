package com.js.hmanager.account.authentication;

import java.time.Instant;

public record AuthenticationTokenResponse(String token, String user, Instant expiration) {
}
