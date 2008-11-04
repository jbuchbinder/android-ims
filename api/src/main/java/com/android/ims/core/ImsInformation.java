// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------

package com.android.ims.core;

import com.android.ims.ImsException;

/**
 * The ImsInformation interface is used for transmitting information
 * to a remote endpoint.
 * <p>
 */
public interface ImsInformation extends ImsServiceMethod {

    enum State {
        /** Initial State */
        INITIATED,
        /** A info request has been sent or received, but not yet answered. */
        INFORMING,
        /** The info request has been answered **/
        INFORMED
    }
    
    /**
     * Sends the information to the remote end point.
     * @throws ImsException
     *             if the INFO method can not be sent.
     */
    public void inform() throws ImsException;

    /**
     * Accepts the incoming INFOrmation from the remote endpoint.
     * @throws ImsException
     */
    public void accept() throws ImsException;
    
    
    /**
     * Sets a listener for this ImsInformation, replacing any previous
     * ImsInformationListener.
     */
    public void setListener(ImsInformationListener listener);
}
