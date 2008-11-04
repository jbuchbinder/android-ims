// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

import com.android.ims.ImsException;

/**
 * Notifications can be used for as part of {@link ImsSubscription} mechanism to
 * inform the subscribed remote entity about events related to the subscription.
 * ImsNotification can be used as for reporting notifications about ongoing
 * {@link ImsReference} requested by the remote party. Subscription handling are
 * mostly handled by the server side, but in case the application developers
 * wishes to develop an point to point IMS application, creation of
 * ImsNotification instances are needed for an established ImsSubscription.
 * 
 */
public interface ImsNotification extends ImsServiceMethod {

    /** The state of the session with the remote endpoint. */
    public enum State {
        /** Initial State */
        INITIATED,
        /**
         * A notification request has been sent or received, but not yet
         * answered.
         */
        NOTIFYING,
        /** In this state the notification has been accepted * */
        NOTIFIED
    }

    /**
     * Sends a notification to the remote endpoint
     */
    void notifyEvent() throws ImsException;

    /**
     * Returns the event related to this notification.
     * 
     * @return a string value.
     */
    String getEvent();

    /**
     * Returns the ImsServiceMethod instance associated to this notification.
     * <p>
     * For instance, if the notification is the result of subscriptions, the
     * service method associated to it is an ImsSubscription. If the
     * notification is the result of reference, the service method associated to
     * it is an ImsReference.
     * 
     * @return an ImsServiceMethod instance.
     */
    public ImsServiceMethod getServiceMethod();

}
