// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

import com.android.ims.ImsException;

/**
 * The Message interface provides functionality of reading headers and body
 * parts of previously transferred messages. A Message can be retrieved by
 * calling <code>ImsServiceMethod.getRequest</code> or
 * <code>ImsServiceMethod.getResponses</code>.
 */
public interface ImsMessage {

    /** Returns the value(s) of a header in this message. */
    String[] getHeader(String header);
    
    /** Returns the value of a header parameter if present. null otherwise * */
    String getHeaderParameter(String header, String key);

    /** Returns the status code of the response. */
    int getStatusCode() throws ImsException;

    /** Returns the reason phrase of the response. */
    String getReasonPhrase() throws ImsException;

    /** Return all body parts * */
    ImsMessageBody[] getBodyParts();

    /**
     * Returns the bodypart with given content type. If there are more than one
     * body part with the same content type, the first one encountered will be
     * returned.
     */
    ImsMessageBody getBodyPart(String contentType);
}