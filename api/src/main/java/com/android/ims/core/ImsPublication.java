// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

import com.android.ims.ImsException;


/**
 * The Publication is used for publishing event state to a remote endpoint and
 * for handling of publishing events received from a remote endpoint. The
 * implementation follows RFC 3903.
 * <p>
 * When the ImsPublication is created (using {@link ImsCoreService.createPublication()} or
 * {@link ImsCoreServiceListener.publicationReceived()}), an event package is set to it 
 * which identifies the type of content associated to this publication. "presence" is a 
 * typical event package - RFC 3856 -.
 * <p>
 * When using ImsCoreService.createPublication(), the ImsPublication is set to expire
 * in 3600 seconds (as default)  unless setExpirationTime() method is called. Users of this interface must have into
 * account that the remote endpoint may reduce the expiration time, in such case, after 
 * ImsPublicationListener.publishDelivered() is called, the user is able to check what the
 * real expiration time is using getExpirationTime().
 * <p>
 * The state machine for ImsPublication is illustrated in the following diagram:
 * <p><img src="./doc-files/publish-state.jpg"/> </p>
 * Example usage of sending a publication:
 * <pre>
 * ImsPublication publication = coreService.createPublication(fromUid,&quot;presence&quot;);
 * ImsMessageBodyPart bodyPart = publication.createBodyPart();
 * bodypart.setContent(pidfDoc.getBytes());
 * bodypart.setHeader(&quot;Content-Type&quot;, &quot;application/pidf+xm&quot;);
 * publication.publish();
 * </pre>
 * @see ImsServiceMethod, ImsPublicationListener
 */
public interface ImsPublication extends ImsServiceMethod {


    /** The life cycle stages of a ImsPublication. */
    public enum State {
        /** The initial state of the publication. */
        INITIAL, 
        /** A publication or unpublication request has been sent. */
        PUBLISHING, 
        /** The publication is publishing information. */
        PUBLISHED, 
        /** The publication is not publishing information anymore. */
        EXPIRED,
        /** The publication has been terminated to presence information. */
        TERMINATED
    }

    /**
     * Sets a listener for this Publication, replacing any previous
     * ImsPublicationListener.
     */
    void setListener(ImsPublicationListener listener);
    
    ImsPublicationListener getPublicationListener();

    /**
     * Sends an initial publication, modify publication  or refresh
     * publication request.
     * <p>
     * If the current state is INITIAL then an initial publication (without SIP-If-Match)
     * is sent. If the state is PUBLISHED and a body part is present 
     * then a modify publication (including SIP-If-Match) is sent. If the state is 
     * PUBLISHED and no bodypart is present then a refresh (including SIP-If-Match)
     * publication is sent.
     * <p>
     * On successful return the state is changed to PUBLISHING.
     * 
     * @throws IllegalStateException
     *             If the state is not INITIAL or PUBLISHED.
     */
    void publish() throws ImsException;
    
    /**
     * Terminates this publication.
     * <p>
     * On successful return the state is changed to PUBLISHING.
     * 
     * @throws IllegalStateException
     *             If the state is not PUBLISHED.
     */
    void unpublish() throws ImsException;
    
    /**
     * Returns the event package corresponding to this publication.
     * @return the event package name.
     */
    public String getEvent(); 

    /** 
     * Returns the current state. 
     * @return the current state
     **/
    State getState();
    
    
    /**
     * Sets the expiration time.
     * 
     * 
     * @param expiration time in seconds.
     * @throws java.lang.InvalidArgumentException
     *             If expiration time is not valid.
     * @throws IllegalStateException
     *             If the state is not INITIAL or PUBLISHED.             
     */
    public void setExpirationTime(long seconds);
    
    /**
     * Returns the expiration time. If state is PUBLISHING, expiration time
     * returned is the same one that was set by the user of this interface. If
     * state is PUBLISHED, the value returned corresponds to the real expiration
     * time approved by the remote endpoint.
     * @return expiration time in seconds.
     */
    public long getExpirationTime();
}
   