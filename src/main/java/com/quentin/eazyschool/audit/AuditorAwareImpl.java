package com.quentin.eazyschool.audit;

import com.quentin.eazyschool.constants.EazySchoolConstants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.of(EazySchoolConstants.ANONYMOUS);
        }
        return Optional.of(auth.getName());
    }
}
