package com.autumn.runtime.session;

import com.autumn.runtime.session.claims.HashMapIdentityClaims;
import com.autumn.runtime.session.claims.IdentityClaims;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * 默认的用户
 *
 * @author 老码农
 * <p>
 * 2017-11-04 15:03:17
 */
@Setter
@Getter
public class DefaultAutumnUser implements AutumnUser {

    /**
     *
     */
    private static final long serialVersionUID = 1022386559812921960L;

    private Long id;

    private String userName;

    /**
     * {@link com.autumn.security.constants.UserStatusConstants}
     */
    private Integer status;

    private String password;

    private String identityType;

    private AutumnUserDeviceInfo deviceInfo;

    private IdentityClaims identityClaims;

    private Set<String> roles;

    private Set<String> permissions;

    /**
     *
     */
    public DefaultAutumnUser() {
        this.setIdentityClaims(new HashMapIdentityClaims());
        this.setRoles(new HashSet<>(16));
        this.setPermissions(new HashSet<>(500));
    }

}
