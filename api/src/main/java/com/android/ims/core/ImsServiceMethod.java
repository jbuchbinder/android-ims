// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

/**
 * The ServiceMethod interface provides methods to manipulate the next outgoing
 * request message and to inspect previously sent request and response messages.
 * The headers and body parts that are set will be transmitted in the next
 * message that is triggered by an interface method.
 * 
 */
public interface ImsServiceMethod {
    enum Method {
        ACK, BYE, CANCEL, INFO, INVITE, MESSAGE, NOTIFY, OPTIONS, PRACK, PUBLISH, REFER, REGISTER, SUBSCRIBE, UPDATE
    }

    /**
     * Adds a header value, either on a new header or appending a new value to
     * an already existing header.
     * 
     * @param key
     *            the header name.
     * @param value
     *            the header value.
     */
    void addHeader(String key, String value);

    /**
     * This method enables the user to inspect the most recent received request
     * message.
     */
    ImsMessage getRequest();

    /**
     * This method enables the user to inspect a previously received request
     * message for the given method.
     */
    ImsMessage getRequest(Method m);

    /**
     * This method enables the user to inspect the most recent received response
     * message.
     */
    ImsMessage getResponse();

    /**
     * This method enables the user to inspect previously received response
     * messages.
     */
    ImsMessage[] getResponses();

    /**
     * Creates a new MessageBodyPart. It will be added to the next outgoing send
     * request/response.
     */
    ImsMessageBody createBodyPart();

    /** Adds an existing bodyPart. */
    void addBodyPart(ImsMessageBody bodyPart);

    /** Returns the user identity of the remote endpoint of this ServiceMethod. * */
    public String getRemoteUserId();

}
