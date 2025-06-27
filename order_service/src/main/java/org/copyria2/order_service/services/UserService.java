package org.copyria2.order_service.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    public Set<String> getAssignedShopIds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();

        Map<String, Object> attributes = jwt.getClaimAsMap("attributes");

        if (attributes == null) {
            return Set.of();
        }

        List<String> roles = (List<String>) attributes.getOrDefault("user_assigned_shop_ids", List.<String>of());

        return new HashSet<>(roles);
    }
    public boolean isPremium(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Map<String, Object> attributes = jwt.getClaimAsMap("realm_access");
        if(attributes == null) {
            return false;
        }
        List<String> roles = (List<String>)  attributes.getOrDefault("roles", List.<String>of());
        return roles.contains("PREMIUM");
    }
    public String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String attributes = jwt.getClaim("email");
        if(attributes == null) {
            return "";
        }
        return attributes;
    }
}
