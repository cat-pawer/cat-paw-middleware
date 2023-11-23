package com.catpaw.catpawmiddleware.config;

import com.catpaw.catpawcore.domain.security.MemberContexts;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EntityScan(basePackages = "com.catpaw.catpawcore.domain.entity")
public class JpaConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAwareImpl();
    }

    static class AuditorAwareImpl implements AuditorAware<Long> {

        @NonNull
        @Override
        public Optional<Long> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null ||
                    authentication.getPrincipal() == null ||
                    !(authentication.getPrincipal() instanceof MemberContexts memberContext)) {
                return Optional.empty();
            }

            return Optional.of(memberContext.getMemberContext().getId());
        }
    }
}
