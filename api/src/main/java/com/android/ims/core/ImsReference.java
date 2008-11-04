// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

import com.android.ims.ImsException;

/**
 * The ImsReferencing is used for referencing to resources of a remote endpoint.
 * The implementation follows RFC3515.
 *  
 */
public interface ImsReference extends ImsServiceMethod {

    /** The state of the session with the remote endpoint. */
    public enum State {
        /** Initial State */
        INITIATED,
        /** A reference request has been sent or received, but not yet answered. */
        PROCEEDING,
        /**
         * In this state the Reference has been accepted and the remote endpoint
         * is referring to the third party *
         */
        REFERRING,
        /** In this state the Reference has been rejected or terminated * */
        TERMINATED

    }

    /**
     * Returns the user identity to refer to.
     * 
     * @return the user identity
     */
    public String getReferToUserId();

    /**
     * Returns the current state of this Reference.
     * 
     * @return one of the enumerated State.
     */
    public State getState();

    /**
     * Sends the reference request to the remote endpoint.
     * 
     * @throws ImsException
     *             If the reference can not be started.
     * 
     * @throws IllegalStateException
     *             if the ImsReference is not in INITIATED state.
     */
    public void refer() throws ImsException;
    
    
    
    /**
     * Returns the reference method to be used. 
     * @return the reference method.
     */
    public String getReferMethod();
    

    /**
     * Accepts an incoming reference request.
     * 
     * @throws ImsException
     *             if acceptance can not be issued.
     * @throws IllegalStateException
     *             if the ImsReference is not in PROCEEDING state.
     */
    public void accept() throws ImsException;

    /**
     * Rejects an incoming reference request.
     * 
     * @throws ImsException
     *             if rejection can not be issued.
     * @throws IllegalStateException
     *             if the ImsReference is not in PROCEEDING state.
     */
    public void reject() throws ImsException;

    /**
     * Sets a listener for this ImsReference, replacing any previous
     * ImsReferenceListener. A null value is allowed and has the effect of
     * removing any existing listener
     * 
     * @param listener
     *            the listener to set, or null
     */
    public void setListener(ImsReferenceListener listener);
    
    /**
     * Creates a new ImsNotification object which can be used to notify
     * to the subscriber about the the state of this reference.
     * @return a ImsNotificaton object.
     * @throws IllegalStateException -
     *             if the Subscription is not in SUBSCRIBED state 
     * @throws ImsException
     *             if the ImsNotification object can not be created.             
     */
    public ImsNotification createNotification() throws ImsException; 
}