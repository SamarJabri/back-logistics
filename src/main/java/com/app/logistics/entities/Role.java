package com.app.logistics.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{

	Commercial, Magasinage;
	@Override
    public String getAuthority() {
        return "Role_" + name();
    }
}
