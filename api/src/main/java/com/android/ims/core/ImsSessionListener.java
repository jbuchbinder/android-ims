// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

/**
 * A listener type for receiving notification on session events.
 */
public interface ImsSessionListener {

    /**
     * Notifies the application that the remote endpoint terminated the session.
     * @param imsSession
     *            the concerned ImsSession 
     */    
    public void sessionTerminated(ImsSession session);

    /** 
     * Notifies the application that the session has been established.
     * @param session the related session. 
     **/
    public void sessionStarted(ImsSession session);

    /**
     * Notifies the application that the session could not be established.
     * @param session the related session.
     */
    public void sessionStartedFailed(ImsSession session);

    /**
     * Notifies the application that the remote endpoint requests an update of
     * the sessions settings. Both re-INVITE's and PRACKS (when any offer is
     * present) are notified to the application using this callback method. In
     * order to apply the requested changes, user must call the accept() method
     * of the related ImsSession.
     * 
     * @param session
     *            the concerned ImsSession.
     */
    void sessionUpdateRequest(ImsSession session);
    
    
    /**
     * Notifies the application that the session has been updated.
     * @param session
     *            the concerned ImsSession.
     */    
    void sessionUpdated(ImsSession session);

    /**
     * Notifies the application that the remote endpoint rejected the session
     * update request.
     */
    void sessionUpdateFailed(ImsSession session);

    /**
     * Notifies the application that a reference request has been received 
     * from a remote endpoint. 
     * @param session the concerned Session
     * @param reference the ImsReference representing the request
     */
    void sessionReferenceReceived(ImsSession session, ImsReference reference);

    /**
     * Notifies the application that a provisional response have been received.
     * This callback method is used when the applications needs to be aware of
     * any provisional response such us Ringing signal, or Session Progress
     * (used for early media for instance) or call forwarding, etc. In some
     * cases, the communication between the endpoints is based on reliable
     * mechanism, and provisional responses must be accepted by the application.
     * In some cases, the parameter needsAcceptance is used to notify the
     * application that this response must be accepted (calling the accept()
     * method of the concerned session).
     * 
     * @param imsSession
     *            the concerned ImsSession
     * @param message
     *            the response received.
     * @param if
     *            true, this provisional response must be accepted by the
     *            session.
     */
    void sessionProvisionalResponse(ImsSession imsSession, ImsMessage message,
            boolean needsAcceptance);

}
