package com.example.springsecurityinaction.security.csrf;

import com.example.springsecurityinaction.domain.UserToken;
import com.example.springsecurityinaction.repository.UserTokenRepository;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

@RequiredArgsConstructor
public class CsrfTokenRepositoryImpl implements CsrfTokenRepository {

    public static final String CSRF_HEADER_NAME = "X-CSRF-TOKEN";
    public static final String CSRF_PARAMETER_NAME = "_csrf";

    public static final String CSRF_TOKEN_REQUEST_HEADER_NAME = "X-IDENTIFIER";

    private final UserTokenRepository userTokenRepository;

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken(CSRF_HEADER_NAME, CSRF_PARAMETER_NAME, uuid);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request,
        HttpServletResponse response) {
        String identifier = request.getHeader(CSRF_TOKEN_REQUEST_HEADER_NAME);

        Optional<UserToken> userToken = userTokenRepository.findByIdentifier(identifier);

        if (userToken.isPresent()) {
            if (token == null) {
                userTokenRepository.delete(userToken.get());
            } else {
                userToken.get().updateToken(token.getToken());
            }
        } else {
            userTokenRepository.save(
                new UserToken(identifier, token.getToken())
            );
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String identifier = request.getHeader(CSRF_TOKEN_REQUEST_HEADER_NAME);

        return userTokenRepository.findByIdentifier(identifier)
            .map(token ->
                new DefaultCsrfToken(CSRF_HEADER_NAME, CSRF_PARAMETER_NAME, token.getToken())
            ).orElse(null);
    }
}
