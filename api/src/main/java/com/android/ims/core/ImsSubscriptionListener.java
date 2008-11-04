// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

public interface ImsSubscriptionListener {

    /**
     * Notifies the application of published event state.
     * @param subscription - the concerned Subscription.
     * @param message - the notification message.
     */
    void subscriptionNotify(ImsSubscription subscription,
            ImsMessage message);

    /**
     * Notifies the application that the subscription was successfully started.
     */
    void subscriptionStarted(ImsSubscription subscription);

    /**
     * Notifies the application that the subscription was not successfully
     * started.
     */
    void subscriptionStartedFailed(ImsSubscription subscription);

    /**
     * Notifies the application that a subscription was terminated.
     * @param subscription - the concerned Subscription.
     */
    void subscriptionTerminated(ImsSubscription subscription);
    
    /**
     * Notifies the application that a subscription refresh has been
     * issued by the remote endpoint.
     * @param subscription - the concerned subscription.
     */
    void subscriptionUpdated(ImsSubscription subscription);
}