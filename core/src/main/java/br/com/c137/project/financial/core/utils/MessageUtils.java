package br.com.c137.project.financial.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MessageUtils {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    public String getIdsMessage(String key, Set<UUID> ids) {
        String formatedIds = ids.stream()
                .map(UUID::toString)
                .collect(Collectors.joining(", "));
        return messageSource.getMessage(
                key,
                new Object[]{formatedIds},
                LocaleContextHolder.getLocale());
    }
}
