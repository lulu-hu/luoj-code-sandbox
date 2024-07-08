package com.lulu.luojcodesandbox.seurity;

import java.security.Permission;

/**
 * @author lulu
 * @version 1.0
 * @description 禁用所有权限安全管理器
 * @date 2024/7/4 13:40
 */
public class DefaultSecurityManager extends SecurityManager {

    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("权限不足" + perm.getActions());
    }
}
