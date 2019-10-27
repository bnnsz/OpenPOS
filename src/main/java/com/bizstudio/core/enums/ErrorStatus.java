/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.enums;

/**
 *
 * @author obinna.asuzu
 */
public enum ErrorStatus {

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
     * You cannot delete your account
     */
    cannot_delete_self(18, "You cannot delete your account"),
    /**
     * Account is disabled
     */
    account_disabled(19, "Account is disabled"),
    /**
     * Rating not found
     */
    rating_not_found(20, "Rating not found"),
    /**
     * Account registeration failed
     */
    cannot_register_account(21, "Account registeration failed"),
    /**
     * Message was not found
     */
    message_not_found(22, "Message was not found"),
    /**
     * User account required
     */
    valid_account_required(23, "Valid account is required"),
    /**
     * Account is already active
     */
    account_verified(24, "Account is already verified"),
    /**
     * Account is already active
     */
    account_not_verified(25, "Account is not verified"),
    /**
     * You cannot feature your account
     */
    cannot_feature_self(26, "You cannot feature your account"),
    /**
     * You cannot unfeature your account
     */
    cannot_unfeature_self(26, "You cannot unfeature your account"),;

    int code;

    String message;

    private ErrorStatus(int code, String message) {
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


