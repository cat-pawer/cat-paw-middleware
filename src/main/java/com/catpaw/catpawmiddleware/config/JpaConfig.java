package com.catpaw.catpawmiddleware.config;

import com.catpaw.catpawmiddleware.domain.security.MemberContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAwareImpl();
    }

    static class AuditorAwareImpl implements AuditorAware<Long> {
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
