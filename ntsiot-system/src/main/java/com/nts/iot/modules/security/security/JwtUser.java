package com.nts.iot.modules.security.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author jie
 * @date 2018-11-23
 */
@Getter
@AllArgsConstructor
@ApiModel("用户")
public class JwtUser implements UserDetails {

    private final Long id;

    @ApiModelProperty("用户名称")
    private final String username;

    @JsonIgnore
    private final String password;

    @ApiModelProperty("用户图像")
    private final String avatar;

    @ApiModelProperty("用户邮箱")
    private final String email;

    @ApiModelProperty("用户电话")
    private final String phone;

    @JsonIgnore
    private final Collection<SimpleGrantedAuthority> authorities;

    private final boolean enabled;

    private Timestamp createTime;

    @JsonIgnore
    private final Date lastPasswordResetDate;

    public final Long customTypeId;



    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Collection getRoles() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
