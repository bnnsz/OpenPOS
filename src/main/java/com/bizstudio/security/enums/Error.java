/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.enums;

/**
 *
 * @author obinna.asuzu
 */
public enum Error {

    /**
     * Account is already active
     */
    account_active(1, "Account is already active"),
    /**
     * Token is expired
     */
    token_expired(2, "Token is expired"),
    /**
     * Invalid token
     */
    token_invalid(3, "Invalid token"),
    /**
     * User already exist
     */
    user_exist(4, "User already exist"),
    /**
     * User does not exist
     */
    user_not_exist(5, "User does not exist"),
    /**
     * You cannot deactivate a system user
     */
    cannot_deactivate_sys_user(6, "You cannot deactivate a system user"),
    /**
     * Role already exists
     */
    role_exist(7, "Role already exists"),
    /**
     * Role does not exist
     */
    role_not_exist(8, "Role does not exist"),
    /**
     * Invalid Authority.Valid format: ROLE.PERMISSION_ACCESS Example:
     * 'ADMIN.USER_READ'
     */
    invalid_grant_format(9, "Invalid Authority.Valid format: ROLE.PERMISSION_ACCESS Example: 'ADMIN.USER_READ'"),
    /**
     * Invalid Authority: Allowed characters; a to z, A to Z, 0 to 9,'_' '_' and
     * '-' Examples: 'ADMIN.USER_READ', 'ADMIN.WEB-SOCKET_READ'
     */
    invalid_grant_characters(10, "Invalid Authority: Allowed characters; a to z, A to Z, 0 to 9,'_' '_' and '-'  Examples: 'ADMIN.USER_READ', 'ADMIN.WEB-SOCKET_READ'"),
    /**
     * Invalid Authority: Must end with '_READ' or '_WRITE' Examples:
     * 'ADMIN.USER_READ', 'ADMIN.WEB-SOCKET_WRITE'
     */
    invalid_grant_access(11, "Invalid Authority: Must end with '_READ' or '_WRITE'  Examples: 'ADMIN.USER_READ', 'ADMIN.WEB-SOCKET_WRITE'"),
    /**
     * Privilege already exist
     */
    privilege_exist(12, "Privilege already exist"),
    /**
     * Privilege does not exist
     */
    privilege_not_exist(13, "Privilege does not exist"),
    /**
     * Cannot delete roles in use
     */
    cannot_del_roles_inuse(14, "Cannot delete roles in use"),
    /**
     * Cannot delete privileges in use
     */
    cannot_del_privileges_inuse(15, "Cannot delete privileges in use"),
    /**
     * Cannot delete system roles
     */
    cannot_delete_sys_role(16, "Cannot delete system roles"),
    /**
     * Cannot delete system privileges
     */
    cannot_delete_sys_privileges(17, "Cannot delete system privileges"),
    /**
     * Cridential expired
     */
    credential_expired(18, "Cridential expired"),;
    ;

    int code;

    String message;

    private Error(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.code + " " + name();
    }

}
