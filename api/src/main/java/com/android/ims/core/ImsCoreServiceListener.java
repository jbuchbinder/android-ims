// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

/**
 * A listener type for receiving notifications about remotly initiated requests
 * and events regarding service registration state.
 */
public interface ImsCoreServiceListener {
    
    /**
     * Notifies the application when a page message is received from a remote
     * endpoint.
     * @param service The concerned service.
     * @param message The received page message.
     */
    public void processPageMessageReceived(ImsCoreService service,
	    ImsPageMessage message);

    /**
     * Notifies the application when a session invitation is received from a
     * remote endpoint.
     * @param service The concerned service.
     * @param session The received session invitation.
     */
    public void processSessionInvitation(ImsCoreService service,
	    ImsSession session);

    
    /**
     * Notifies the application when a publication is received from a remote
     * endpoint.
     * @param service The concerned service.
     * @param publication The received publication.
     */    
    void publicationReceived(ImsCoreService service, ImsPublication publication);
    
    /**
     * Notifies the application when a reference is received from a remote
     * endpoint.
     * @param service The concerned service.
     * @param reference The received reference.
     */        
    void referenceReceived(ImsCoreService service, ImsReference reference);
    
    /**
     * Notifies the application when a subscription is received from a
     * remote endpoint.
     * @param service The concerned service.
     * @param subscription The received subscription.
     */
    void subscriptionReceived(ImsCoreService service,
        ImsSubscription subscription);    
}
