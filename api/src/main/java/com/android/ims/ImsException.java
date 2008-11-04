// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------

package com.android.ims;

/** Indicates an unexpected error condition in a method. */
public class ImsException extends Exception {

    private static final long serialVersionUID = 2245824065975157915L;

    /** Constructs an ImsException without an error message. */
    public ImsException() {
    }

    /** Constructs an ImsException with an error message. */
    public ImsException(String message) {
        super(message);
    }

    /** Constructs an ImsException without an error message but with cause. */
    public ImsException(Throwable cause) {
        super(cause);
    }

    /** Constructs an ImsException with both an error message and cause. */
    public ImsException(String message, Throwable cause) {
        super(message, cause);
    }

}