// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

import com.android.ims.ImsException;

/**
 * The ImsPageMessage interface is used for simple instant messages (RFC 3428).
 * <p>
 * Example usage of sending a page message:
 * <pre>
 * ImsPageMessage page = coreService.createPageMessage(fromUid, toUid);
 * page.setListener(pageMessageListener);
 * page.send("Hi".getBytes(), "text/plain");
 * </pre>
 * Example usage of receiving a page message:
 * <pre>
 * public void pageMessageReceived(ImsCoreService service,
 *      ImsPageMessage message) {
 *   ...   
 *   message.delivered();
 * }
 * </pre>
 * @see ImsServiceMethod, ImsCoreServiceListener, ImsPageMessageListener
 */
public interface ImsPageMessage extends ImsServiceMethod {
    
    /** The life cycle stages of a page message. */
    public enum State {
        /** Indicates the initial state of the message. */
        INITIAL,
        /** Indicates that the page message is being sent **/
        SENDING,
        /** Indicates that the page message was received. */
        RECEIVED,
        /** Indicates that the page message was not received. */
        FAILED
    }

    /**
     * Sets a listener for this PageMessage, replacing any previous
     * PageMessageListener.
     */
    void setListener(ImsPageMessageListener listener);

    /**
     * Sends a page message.
     * <p>
     * The ImsPageMessage will transit to SENDING state after calling this method
     * @param content
     *            The content of the page message.
     * @param contentType
     *            The content MIME type.
     * @throws IllegalStateException
     *             If the state is not INITIAL.
     * @throws ImsException
     *             If the page message cannot be sent.
     */
    void send(byte[] content, String contentType) throws ImsException;

    /**
     * Sends a page message. in case body parts have been created, they 
     * are included in the message itself.   
     * <p>
     * The ImsPageMessage will transit to SENDING state after calling this method 
     * @throws IllegalStateException
     *             If the state is not INITIAL. 
     * @throws ImsException
     *             If the page message cannot be sent.
     */
    void send() throws ImsException;

    /**
     * Returns the content from the received page message.
     * 
     * @throws IllegalStateException
     *             If the state is not SENT.
     */
    byte[] getContent();

    /**
     * Returns the content MIME type of the received page message.
     * 
     * @throws IllegalStateException
     *             If the state is not SENT.
     */
    String getContentType();

    /** Returns the life cycle stage of this page message. */
    State getState();

    /**
     * Marks the page message as being delivered to this endpoint.
     * <p>
     * The ImsPageMessage will transit to RECEIVED state after calling this method 
     * @throws IllegalStateException
     *             If the state is not SENDING.
     */
    void received() throws ImsException;

    /**
     * Rejects a received page message.
     * 
     * @throws IllegalStateException
     *             If the state is not SENDING.
     */
    void reject(int errorCode) throws ImsException;
 
}
