package br.com.c137.project.financial.core.utils;

import br.com.c137.project.financial.core.responses.ResponsePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class ServiceUtils {
    public static <T> ResponseEntity<?> pageHasContent(Page<T> page, PagedResourcesAssembler<T> assembler) {
        if (!page.hasContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.OK).body(assembler.toModel(page));
    }

    public static <T> ResponseEntity<ResponsePayload<T>> createResponse(HttpStatus httpStatus, UUID id, T dto, String message) {
        return ResponseEntity.status(httpStatus).body(new ResponsePayload<>(id, message, dto));
    }

    public static UUID getUserIdFromToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return UUID.fromString(jwt.getSubject());
        }
        return null;
    }
}
