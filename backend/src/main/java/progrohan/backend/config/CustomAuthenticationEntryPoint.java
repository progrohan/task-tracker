package progrohan.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import progrohan.backend.dto.ErrorResponseDto;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private static final ErrorResponseDto AUTHENTICATION_ERROR = new ErrorResponseDto("User is not authenticated!");

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String requestUri = request.getRequestURI();


        if (requestUri.equals("/api/auth/sign-up") ||
            requestUri.equals("/api/auth/sign-in") ||
            requestUri.startsWith("/swagger-ui") ||
            requestUri.equals("/v3/api-docs")) {

            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), AUTHENTICATION_ERROR);
    }
}
