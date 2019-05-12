/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.services;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *
 * @author ObinnaAsuzu
 */
public class PinToken extends UsernamePasswordToken{

    /**
     * The pin, in char[] format
     */
    private char[] pin;

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
     * @param pin   the pin character array submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public PinToken( final char[] pin,final boolean rememberMe, final String host) {
        super(null, "".toCharArray(), rememberMe, host);
        this.pin = pin;
    }
    
    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, if the user
 wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     *
     * @param pin   the pin character array submitted for authentication
     * @param username the username submitted for authentication
     * @param password the password character array submitted for authentication
     * @since 1.0
     */
    public PinToken( final String pin,final String username,final String password) {
        this(pin.toCharArray(), username, password.toCharArray(),false, null);
    }
    
    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, if the user
 wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     *
     * @param pin   the pin character array submitted for authentication
     * @param username the username submitted for authentication
     * @param password the password character array submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @since 1.0
     */
    public PinToken( final String pin,final String username,final String password, final boolean rememberMe) {
        this(pin.toCharArray(), username, password.toCharArray(),rememberMe, null);
    }
    
    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, if the user
 wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     *
     * @param pin   the pin character array submitted for authentication
     * @param username the username submitted for authentication
     * @param password the password character array submitted for authentication
     * @param host       the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public PinToken( final String pin,final String username,final String password, final String host) {
        this(pin.toCharArray(), username, password.toCharArray(),false, host);
    }
    
     /**
     * Constructs a new PinToken encapsulating the username and pin submitted, if the user
 wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     *
     * @param pin   the pin character array submitted for authentication
     * @param username the username submitted for authentication
     * @param password the password character array submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public PinToken( final String pin,final String username,final String password, final boolean rememberMe, final String host) {
        this(pin.toCharArray(), username, password.toCharArray(),rememberMe, host);
    }
    
     /**
     * Constructs a new PinToken encapsulating the username and pin submitted, if the user
 wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     *
     * @param pin   the pin character array submitted for authentication
     * @param username the username submitted for authentication
     * @param password the password character array submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public PinToken( final char[] pin,final String username,final char[] password, final boolean rememberMe, final String host) {
        super(username, password, host);
        this.pin = pin;
    }


    /**
     * Constructs a new PinToken encapsulating the username and pin submitted, if the user
     * wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     * <p/>
     * <p>This is a convenience constructor and maintains the pin internally via a character
 array, i.e. <tt>pin.toCharArray();</tt>.  Note that storing a pin as a String
 in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param pin   the pin string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public PinToken( final String pin, final boolean rememberMe, final String host) {
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

    

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    /**
     * Clears out (nulls) the username, pin, rememberMe, and inetAddress.  The pin bytes are explicitly set to
     * <tt>0x00</tt> before nulling to eliminate the possibility of memory access at a later time.
     */
    public void clear() {
        super.clear();

        if (this.pin != null) {
            for (int i = 0; i < pin.length; i++) {
                this.pin[i] = 0x00;
            }
            this.pin = null;
        }

    }

    
}
