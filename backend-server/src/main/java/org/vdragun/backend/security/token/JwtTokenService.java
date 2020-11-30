package org.vdragun.backend.security.token;

import java.util.Map;

public interface JwtTokenService {

    String buildJwtToken(Map<String, Object> claims);

    Map<String, Object> parseJwtToken(String jwt);
}
