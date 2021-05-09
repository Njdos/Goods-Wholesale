package ua.wholesale.web.site.role;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,Seller,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
