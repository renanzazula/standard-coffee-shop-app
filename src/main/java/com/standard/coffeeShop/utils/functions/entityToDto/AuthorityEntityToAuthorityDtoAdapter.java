package com.standard.coffeeShop.utils.functions.entityToDto;


import com.standard.coffeeShop.service.dto.security.AuthorityDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.function.Function;

public class AuthorityEntityToAuthorityDtoAdapter implements Function<GrantedAuthority, AuthorityDto> {

    @Override
    public AuthorityDto apply(GrantedAuthority authorityEntity) {
        AuthorityDto dto = new AuthorityDto();
        dto.setRole(authorityEntity.getAuthority());
        return dto;
    }


}
