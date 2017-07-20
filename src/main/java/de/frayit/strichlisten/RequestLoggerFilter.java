package de.frayit.strichlisten;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Slf4j
public class RequestLoggerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        filterChain.doFilter(requestWrapper, responseWrapper);

        if (logger.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append(requestWrapper.getMethod()).append(" ").append(requestWrapper.getRequestURI()).append(" ").append(requestWrapper.getProtocol()).append("\n");
            Enumeration<String> headerNames = requestWrapper.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = requestWrapper.getHeader(headerName);
                sb.append(headerName).append(": ").append(headerValue).append("\n");
            }
            sb.append("\n");

            String contentTypeHeader = requestWrapper.getHeader("Content-Type");
            MediaType contentType = contentTypeHeader == null ? null : MediaType.parseMediaType(contentTypeHeader);
            boolean textualContentType = contentType != null && (contentType.getType().equals("text")
                    || contentType.isCompatibleWith(MediaType.APPLICATION_JSON)
                    || contentType.isCompatibleWith(MediaType.APPLICATION_XML));

            if (textualContentType) {
                String content = new String(requestWrapper.getContentAsByteArray(), contentType.getCharset() == null ? StandardCharsets.ISO_8859_1 : contentType.getCharset());
                sb.append(content).append("\n");
            } else {
                sb.append("--- Binary Content ---");
            }

            logger.info(sb.toString());
        }
    }
}
