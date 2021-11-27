package com.festu.almanac.model.converters;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class GrantedAuthorityListConverter implements AttributeConverter<List<GrantedAuthority>, String> {

    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<GrantedAuthority> grantedAuthorities) {
        if (grantedAuthorities == null) {
            return "";
        }
        List<String> authorities = grantedAuthorities.stream()
                                                     .map(GrantedAuthority::getAuthority)
                                                     .collect(Collectors.toList());
        return String.join(SPLIT_CHAR, authorities);
    }

    @Override
    public List<GrantedAuthority> convertToEntityAttribute(String string) {
        return string != null && !string.isEmpty()
               ? Arrays.stream(string.split(SPLIT_CHAR))
                       .map(SimpleGrantedAuthority::new)
                       .collect(Collectors.toList())
               : Collections.emptyList();
    }
}
