package com.github.brosander.ignite.playground;

import java.security.Permission;

import javax.management.MBeanTrustPermission;

import org.codehaus.groovy.tools.shell.util.NoExitSecurityManager;

public class PlaygroundSecurityManager extends NoExitSecurityManager {
    public PlaygroundSecurityManager() {
        super(null);
    }

    @Override
    public void checkPermission(Permission perm) {
        super.checkPermission(perm);
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        if (MBeanTrustPermission.class.isInstance(perm) && "register".equals(perm.getName())) {
            return;
        }
        super.checkPermission(perm, context);
    }
}
