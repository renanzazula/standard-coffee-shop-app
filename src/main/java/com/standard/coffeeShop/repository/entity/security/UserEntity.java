package com.standard.coffeeShop.repository.entity.security;

import com.standard.coffeeShop.repository.entity.CustomerEntity;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public  class UserEntity implements UserDetails, CredentialsContainer, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Set<RoleEntity> roles;

    @ManyToOne(fetch = FetchType.EAGER)
    private CustomerEntity customer;

    @Builder.Default
    @Column(name = "accountNonExpired")
    private boolean accountNonExpired = true;

    @Builder.Default
    @Column(name = "accountNonLocked")
    private boolean accountNonLocked = true;

    @Builder.Default
    @Column(name = "credentialNonExpired")
    private boolean credentialNonExpired = true;

    @Builder.Default
    @Column(name = "enable")
    private boolean enable = true;

    @Builder.Default
    @Column(name = "use_google_2fa")
    private boolean useGoogle2fa = false;

    @Column(name = "user_google_2_fa_secret")
    private String userGoogle2faSecret;

    @Getter
    @Builder.Default
    @Transient
    private boolean userGoogle2FaRequired = false;

    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(RoleEntity::getAuthorities)
                .flatMap(Set::stream)
                .map(authorityEntity -> {
                    return new SimpleGrantedAuthority(authorityEntity.getPermission());
                })
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

}