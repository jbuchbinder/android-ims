// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

import com.android.ims.ImsManager;
import com.android.ims.ImsService;
import com.android.ims.core.ImsServiceMethod.Method;

/**
 * The ImsCoreService enables creation of the essential elements of a service.
 * An ImsCoreService serves as a factory creating transactions, dialogs and
 * media connections through the ImsServiceMethods. In addition, by setting a
 * listener the service can respond to incoming IMS methods.
 * 
 * A ImsCoreService is created using the mechanism provided by the ImsManager
 * (see {@link ImsManager}, using a name string in the following form:
 * 
 * coreService = (ImsCoreService)
 *    imsManager.createService(ImsService.SERVICE_TYPE_CORE,"serviceId");
 * 
 */
public interface ImsCoreService extends ImsService {
    /**
     * Creates a new ImsSession.
     * 
     * @param fromUserId
     *            the preferred identity to use, or null to let the system
     *            automatically select one
     * @param remotePUI
     *            The address of remote endpoint.
     * @return a new session.
     */
    public ImsSession createSession(String fromUserId, String remotePUI);

    /**
     * Creates an ImsSubscription with fromUserId as sender and toUserId as the
     * remote endpoint to subscribe event state on.
     * 
     * @param fromUserId
     *            The preferred user identity of the subscribing party.
     * @param toUserId
     *            The user identity of the subscribed party.
     * @param event
     *            The event package to subscribe information on.
     * @return A new subscription service method.
     */    
    public ImsSubscription createSubscription(String fromUserId,
            String remotePUI, String event);

    /**
     * Creates an ImsPageMessage with fromUserId as sender, addressed to
     * toUserId.
     * 
     * @param fromUserId
     *            The preferred user identity of the sending party.
     * @param toUserId
     *            The user identity of the receiving party.
     * @return A new paging message service method.
     */    
    public ImsPageMessage createPageMessage(String fromUserId,
            String remotePUI);

    /**
     * Creates an ImsPublication with fromUserId as sender and toUserId as the
     * remote endpoint identity to publish event state on.
     * 
     * @param fromUserId
     *            The preferred user identity of the sending party. 
     * @param toUserId
     *            The user identity of the publishing party.
     * @param event
     *            The publication event.            
     * @return A new publication service method.
     */    
    public ImsPublication createPublication(String fromUserId,
            String remotePUI, String event);

    public boolean isServiceRegistered();

    /**
     * Creates a Reference with fromUserId as sender, addressed to toUserId and
     * referToUserId as the user identity to refer to.
     * 
     * @param fromUserId
     *            sender user identity.
     * @param toUserId
     *            the remote user identity.
     * @param referToUserId
     *            the user identity to refer to.
     * @param referMethod
     *            the reference method to be used by the reference request, for
     *            example "INVITE"
     * @return a new ImsReference.
     */

    public ImsReference createReference(String fromUserId, String toUserId,
            String referToUserId, Method referMethod);

    /**
     * Returns the local user identity stored in this CoreService
     * 
     * @return
     */
    public String getLocalUserId();

    /**
     * Sets a listener for this service, replacing any previous
     * ImsCoreServiceListener. A null reference is allowed and has the effect of
     * removing any existing listener.
     * 
     * @param listener
     *            The listener to set, or null.
     */
    public void setListener(ImsCoreServiceListener listener);
}
