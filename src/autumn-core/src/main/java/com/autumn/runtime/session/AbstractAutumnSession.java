package com.autumn.runtime.session;

import java.util.Collection;
import java.util.Set;

/**
 * 会话抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-01 11:08
 **/
public abstract class AbstractAutumnSession implements AutumnSession {

    @Override
    public boolean isAuthenticated() {
        return this.getUserId() != null;
    }

    @Override
    public boolean hasRole(String role) {
        Set<String> roles = this.getRoles();
        return roles != null && roles.contains(role);
    }

    private boolean hasAllItems(Set<String> itemSet, boolean isAnd, String... items) {
        if (itemSet == null || itemSet.size() == 0 || items == null || items.length == 0) {
            return false;
        }
        for (String item : items) {
            if (isAnd) {
                if (!itemSet.contains(item)) {
                    return false;
                }
            } else {
                if (itemSet.contains(item)) {
                    return true;
                }
            }
        }
        return isAnd;
    }

    private boolean hasAllItems(Set<String> itemSet, boolean isAnd, Collection<String> items) {
        if (itemSet == null || items == null || itemSet.size() == 0 || items.size() == 0) {
            return false;
        }
        for (String item : items) {
            if (isAnd) {
                if (!itemSet.contains(item)) {
                    return false;
                }
            } else {
                if (itemSet.contains(item)) {
                    return true;
                }
            }
        }
        return isAnd;
    }

    @Override
    public boolean hasAllRoles(String... roles) {
        return this.hasAllItems(this.getRoles(), true, roles);
    }

    @Override
    public boolean hasAllRoles(Collection<String> roles) {
        return this.hasAllItems(this.getRoles(), true, roles);
    }

    @Override
    public boolean hasOrAllRoles(String... roles) {
        return this.hasAllItems(this.getRoles(), false, roles);
    }

    @Override
    public boolean hasOrAllRoles(Collection<String> roles) {
        return this.hasAllItems(this.getRoles(), false, roles);
    }

    @Override
    public boolean isPermitted(String permission) {
        Set<String> permissions = this.getPermissions();
        return permissions != null && permissions.contains(permission);
    }

    @Override
    public boolean isPermittedAll(String... permissions) {
        return this.hasAllItems(this.getPermissions(), true, permissions);
    }

    @Override
    public boolean isPermittedAll(Collection<String> permissions) {
        return this.hasAllItems(this.getPermissions(), true, permissions);
    }

    @Override
    public boolean isOrPermittedAll(String... permissions) {
        return this.hasAllItems(this.getPermissions(), false, permissions);
    }

    @Override
    public boolean isOrPermittedAll(Collection<String> permissions) {
        return this.hasAllItems(this.getPermissions(), false, permissions);
    }
}
