/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.service;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 *
 * @author ObinnaAsuzu
 */
public class PinToken implements HostAuthenticationToken, RememberMeAuthenticationToken{

    /**
     * The pin, in char[] format
     */
    private char[] pin;

    /**
     * Whether or not 'rememberMe' should be enabled for the corresponding login attempt;
     * default is <code>false</code>
     */
    private boolean rememberMe = false;

    /**
     * The location from where the login attempt occurs, or <code>null</code> if not known or explicitly
     * omitted.
     */
    private String host;

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /**
     * JavaBeans compatible no-arg constructor.
     */
    public PinToken() {
    }

    /**
     * Constructs a new PinToken encapsulating the username and pin submitted
 during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and a
     * <tt>rememberMe</tt> default of <tt>false</tt>.
     *
     * @param pin the pin character array submitted for authentication
     */
    public PinToken(final char[] pin) {
        this(pin, false, null);
    }

    /**
     * Constructs a new PinToken encapsulating the username and pin submitted
 during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and
     * a <tt>rememberMe</tt> default of <tt>false</tt>
     * <p/>
     * <p>This is a convenience constructor and maintains the pin internally via a character
 array, i.e. <tt>pin.toCharArray();</tt>.  Note that storing a pin as a String
 in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param pin the pin string submitted for authentication
     */
    public PinToken(final String pin) {
        this(pin != null ? pin.toCharArray() : null, false, null);
    }

    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, the
 inetAddress from where the attempt is occurring, and a default <tt>rememberMe</tt> value of <tt>false</tt>
     *
     * @param username the username submitted for authentication
     * @param pin the pin string submitted for authentication
     * @param host     the host name or IP string from where the attempt is occurring
     * @since 0.2
     */
    public PinToken(final char[] pin, final String host) {
        this(pin, false, host);
    }

    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, the
 inetAddress from where the attempt is occurring, and a default <tt>rememberMe</tt> value of <tt>false</tt>
     * <p/>
     * <p>This is a convenience constructor and maintains the pin internally via a character
 array, i.e. <tt>pin.toCharArray();</tt>.  Note that storing a pin as a String
 in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param username the username submitted for authentication
     * @param pin the pin string submitted for authentication
     * @param host     the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public PinToken(final String pin, final String host) {
        this(pin != null ? pin.toCharArray() : null, false, host);
    }

    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, as well as if the user
 wishes their identity to be remembered across sessions.
     *
     * @param username   the username submitted for authentication
     * @param pin   the pin string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @since 0.9
     */
    public PinToken(final char[] pin, final boolean rememberMe) {
        this(pin, rememberMe, null);
    }

    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, as well as if the user
     * wishes their identity to be remembered across sessions.
     * <p/>
     * <p>This is a convenience constructor and maintains the pin internally via a character
 array, i.e. <tt>pin.toCharArray();</tt>.  Note that storing a pin as a String
 in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param username   the username submitted for authentication
     * @param pin   the pin string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @since 0.9
     */
    public PinToken(final String pin, final boolean rememberMe) {
        this( pin != null ? pin.toCharArray() : null, rememberMe, null);
    }

    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, if the user
 wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     *
     * @param username   the username submitted for authentication
     * @param pin   the pin character array submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public PinToken( final char[] pin,
                                 final boolean rememberMe, final String host) {

        this.pin = pin;
        this.rememberMe = rememberMe;
        this.host = host;
    }


    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, if the user
     * wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     * <p/>
     * <p>This is a convenience constructor and maintains the pin internally via a character
 array, i.e. <tt>pin.toCharArray();</tt>.  Note that storing a pin as a String
 in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param username   the username submitted for authentication
     * @param pin   the pin string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public PinToken( final String pin,
                                 final boolean rememberMe, final String host) {
        this(pin != null ? pin.toCharArray() : null, rememberMe, host);
    }

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

   


    /**
     * Returns the pin submitted during an authentication attempt as a character array.
     *
     * @return the pin submitted during an authentication attempt as a character array.
     */
    public char[] getPin() {
        return pin;
    }

    /**
     * Sets the pin for submission during an authentication attempt.
     *
     * @param pin the pin to be used for submission during an authentication attempt.
     */
    public void setPin(char[] pin) {
        this.pin = pin;
    }

    /**
     * Simply returns {@link #getUsername() getUsername()}.
     *
     * @return the {@link #getUsername() username}.
     * @see org.apache.shiro.authc.AuthenticationToken#getPrincipal()
     */
    public Object getPrincipal() {
        return null;
    }

    /**
     * Returns the {@link #getPin() pin} char array.
     *
     * @return the {@link #getPin() pin} char array.
     * @see org.apache.shiro.authc.AuthenticationToken#getCredentials()
     */
    public Object getCredentials() {
        return getPin();
    }

    /**
     * Returns the host name or IP string from where the authentication attempt occurs.  May be <tt>null</tt> if the
     * host name/IP is unknown or explicitly omitted.  It is up to the Authenticator implementation processing this
     * token if an authentication attempt without a host is valid or not.
     * <p/>
     * <p>(Shiro's default Authenticator allows <tt>null</tt> hosts to support localhost and proxy server environments).</p>
     *
     * @return the host from where the authentication attempt occurs, or <tt>null</tt> if it is unknown or
     *         explicitly omitted.
     * @since 1.0
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the host name or IP string from where the authentication attempt occurs.  It is up to the Authenticator
     * implementation processing this token if an authentication attempt without a host is valid or not.
     * <p/>
     * <p>(Shiro's default Authenticator
     * allows <tt>null</tt> hosts to allow localhost and proxy server environments).</p>
     *
     * @param host the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Returns <tt>true</tt> if the submitting user wishes their identity (principal(s)) to be remembered
     * across sessions, <tt>false</tt> otherwise.  Unless overridden, this value is <tt>false</tt> by default.
     *
     * @return <tt>true</tt> if the submitting user wishes their identity (principal(s)) to be remembered
     *         across sessions, <tt>false</tt> otherwise (<tt>false</tt> by default).
     * @since 0.9
     */
    public boolean isRememberMe() {
        return rememberMe;
    }

    /**
     * Sets if the submitting user wishes their identity (principal(s)) to be remembered across sessions.  Unless
     * overridden, the default value is <tt>false</tt>, indicating <em>not</em> to be remembered across sessions.
     *
     * @param rememberMe value indicating if the user wishes their identity (principal(s)) to be remembered across
     *                   sessions.
     * @since 0.9
     */
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    /**
     * Clears out (nulls) the username, pin, rememberMe, and inetAddress.  The pin bytes are explicitly set to
     * <tt>0x00</tt> before nulling to eliminate the possibility of memory access at a later time.
     */
    public void clear() {
        this.host = null;
        this.rememberMe = false;

        if (this.pin != null) {
            for (int i = 0; i < pin.length; i++) {
                this.pin[i] = 0x00;
            }
            this.pin = null;
        }

    }

    /**
     * Returns the String representation.  It does not include the pin in the resulting
     * string for security reasons to prevent accidentally printing out a pin
     * that might be widely viewable).
     *
     * @return the String representation of the <tt>PinToken</tt>, omitting
         the pin.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" - ");
        sb.append(", rememberMe=").append(rememberMe);
        if (host != null) {
            sb.append(" (").append(host).append(")");
        }
        return sb.toString();
    }
}
