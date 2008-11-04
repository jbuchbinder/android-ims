package com.android.ims.core;

//--------------------------------------------------------------------------
//COPYRIGHT Kike
//--------------------------------------------------------------------------
public interface ImsCapabilities extends ImsServiceMethod {

    enum State {
        /**
         * The capability response is received.
         */
        ACTIVE,
        /**
         * The capability request is sent and the platform is waiting for a response.
         */
        PENDING,
        /**
         * The capability request has not been sent
         */
        INACTIVE
    }

    /**
     * Sends a capability request to a remote endpoint.
     * The Capabilities will transit to PENDING state after calling this method.  
     * @param sdpInRequest  if true, the IMS engine will add a Session Description 
     * Protocol (SDP) to the capability request.
     */
    void queryCapabilities(boolean sdpInRequest);
    
    
    
    /**
     * Returns an array of strings representing valid user identities for the 
     * remote endpoint.
     * @return  an array of user identities
     */
    String[] getRemoteUserIdentities();
    
    /**
     * Returns the current state of this capabilities. 
     * @return the current state of this Capabilities
     */
    State getState();

    
    /**
     * Sets a listener for this Capabilities.
     * @param listener the listener to set, or null.
     */
    void setListener(ImsCapabilitiesListener listener);
}
