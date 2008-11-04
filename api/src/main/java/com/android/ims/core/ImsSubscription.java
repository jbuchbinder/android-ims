// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

import com.android.ims.ImsException;

/**
 * A ImsSubscription is used for subscribing to event state from a remote endpoint or
 * for handling of subscription events received from a remote endpoint. The
 * implementation follows RFC 3856. 
 * <p>
 * When the ImsSubscription is created (using {@link ImsCoreService.createSubscription()} or
 * {@link ImsCoreServiceListener.subscriptionReceived()}), an event package is set to it 
 * which identifies the type of content associated to this publication. "presence" is a 
 * typical event package - RFC 3856 -.
 * <p> 
 * When using ImsCoreService.createSubscription(), the ImsSubscription is set to expire
 * in 3600 seconds (as default) unless setExpirationTime() method is called. Users of this interface must 
 * have into account that the remote endpoint may change the expiration time, in such case, after 
 * ImsSubscriptionListener.subscriptionStarted() is called, the user is able to check what the
 * real expiration time is using getExpirationTime().
 * <p> 
 * The state machine for ImsSubscription is illustrated in the following diagram:
 * <p><img src="./doc-files/subscribe-state.jpg"/> </p>
 * Example usage of creating an ImsSubscription
 * <pre>
 * ImsSubscription subscription = coreService.createSubscription(fromUid,toUid, &quot;presence&quot;);
 * subscription.subscribe();  
 * </pre>
 * <p>
 * Example usage of receiving an ImsSubscription
 * <pre>
 * ....
 * public subscriptionReceived(ImsCoreService coreService, ImsSubscription subscription) {
 *      // Accept it.
 *      subscription.accept();
 *      ImsNotification notification = subscription.createActiveNotification();
 *      ...
 *      // Add notification information
 *      ...
 *      notification.notifyEvent();
 *      ...
 * </pre>
 * @see ImsServiceMethod, ImsSubscriptionListener, ImsNotification 
 */
public interface ImsSubscription extends ImsServiceMethod {
    /** The life cycle stages of a presence watcher. */
    public enum State {
        /** The initial state of the presence watcher. */
        INITIAL,
        /** A subscribe or unsubscribe request has been sent. */
        SUBSCRIBING,
        /** The watcher is subscribed to presence information of a presentity.*/
        SUBSCRIBED,
        /** The watcher is not subscribed to presence information. */
        EXPIRED,
        /** The watcher is not subscribed to presence information. */
        TERMINATED
    }

    /** 
     * Returns the current state of the state machine of the Subscription.
     * @return the current state 
     **/
    State getState();

    /**
     * Sends a subscription request.
     * On successful return the state is changed to SUBSCRIBING.
     * @throws ImsException 
     * 
     * @throws IllegalStateException -
     *             if the Subscription is not in INITIAL or SUBSCRIBED state
     */    
    public void subscribe() throws ImsException ;

    /** 
     * Terminates the subscription.
     * @throws IllegalStateException -
     *             if the Subscription is not in SUBSCRIBED state
     **/    
    public void unsubscribe() throws ImsException ;

    /**
     * Sets a listener for this ImsSubscription, replacing any previous
     * ImsSubscriptionListener.
     * 
     * @param listener -
     *            the listener to set, or null
     */
    public void setListener(ImsSubscriptionListener listener);

    /**
     * Returns the event package corresponding to this Subscription.
     * @return the event package name.
     */    
    public String getEvent();
    
    /**
     * Sets the expiration time.
     * This expires value indicates the duration of the subscription.
     * 
     * @param value
     *            the expiration time value.
     * 
     * @throws java.lang.InvalidArgumentException
     *             If expiration time is not valid.
     * 
     */
    public void setExpirationTime(long value);

    /**
     * Returns the expiration time.
     * @return a numeric value representing the expiration time.
     */
    public long getExpirationTime();    
}
