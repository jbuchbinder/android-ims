// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

import java.util.Set;

/**
 * A MessageBody can contain different kinds of content, for example text,
 * an image or an audio clip
 */
public interface ImsMessageBody {
    public byte[] getContent();

    public void setContent(byte[] content);

    public String getHeader(String contentType);

    public void setHeader(String contentType, String value);

    public Set<String> getHeaders();
}
