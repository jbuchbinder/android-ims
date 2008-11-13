// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------

package stack.com.android.ims.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.android.ims.core.ImsMessageBody;

/**
 * Utility to deal with multipart MIME messages.
 */
public class MimeUtility {

    class ByteArrayDataSource implements DataSource {

        private ByteArrayInputStream stream;
        private String contentType;

        public ByteArrayDataSource(byte[] content, String contentType) {
            stream = new ByteArrayInputStream(content);
            this.contentType = contentType;
        }

        /** {@inheritDoc} */
        public String getContentType() {
            return contentType;
        }

        /** {@inheritDoc} */
        public InputStream getInputStream() {
            return stream;
        }

        /** {@inheritDoc} */
        public String getName() {
            return "";
        }

        /** {@inheritDoc} */
        public OutputStream getOutputStream() {
            return null;
        }
    }

    /**
     * Splits a MultiPart MIME message into separate bodyparts.
     * 
     * @param content
     *            The content of the MultiPart MIME message.
     * @param contentType
     *            The content type including start and boundary.
     * @return An array of ImsMessageBodyParts.
     */
    public ImsMessageBody[] parseMultipart(byte[] content,
            String contentType) {
        try {
            DataSource dataSource = new ByteArrayDataSource(content,
                    contentType);
            MimeMultipart mmp = new MimeMultipart(dataSource);
            ImsMessageBody[] result = new ImsMessageBody[mmp.getCount()];
            for (int i = 0; i < mmp.getCount(); i++) {
                ImsMessageBody imbp = new ImsMessageBodyImpl();
                BodyPart bp = mmp.getBodyPart(i);
                imbp.setContent(getContent(bp));
                Enumeration<?> headers = bp.getAllHeaders();
                while (headers.hasMoreElements()) {
                    Header header = (Header) headers.nextElement();
                    imbp.setHeader(header.getName(), header.getValue());
                }
                result[i] = imbp;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(); // TODO ignore for now
            return new ImsMessageBody[0];
        }
    }

    public MimeMultipart createMultipart(Vector<ImsMessageBody> parts) {
        ImsMessageBody[] array = new ImsMessageBody[parts.size()];
        return createMultipart(parts.toArray(array));
    }

    public MimeMultipart createMultipart(ImsMessageBody[] parts) {
        assert parts != null && parts.length > 1;
        MimeMultipart mmp = new MimeMultipart();
        for (ImsMessageBody part : parts) {
            try {
                MimeBodyPart bp = new MimeBodyPart();
                //bp.setContent(part.getContent(), part.getHeader(Constants.CONTENT_TYPE));
                String data = new String(part.getContent());
                bp.setContent(data, "text/plain");
                for (String header : part.getHeaders()) {
                        bp.addHeader(header, part.getHeader(header));
                }
                mmp.addBodyPart(bp);
            } catch (MessagingException exception) {
                exception.printStackTrace();
            }
        }
        return mmp;
    }
    
    public byte[] getContent(MimeMultipart mmp) throws IOException, MessagingException {
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        mmp.writeTo(stream);
        return stream.toByteArray();
    }
    
    private byte[] getContent(BodyPart bp) throws IOException,
            MessagingException {
        ByteArrayInputStream bais = (ByteArrayInputStream) bp.getContent();
        StringBuffer sb = new StringBuffer();
        while (bais.available() > 0) {
            sb.append((char) bais.read());
        }
        return sb.toString().getBytes();
    }

}
