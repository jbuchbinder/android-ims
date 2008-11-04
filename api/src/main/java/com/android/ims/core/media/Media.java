//---------------------------------------
//COPYRIGHT Kike
//--------------------------------------------------------------------------
package com.android.ims.core.media;



/**
 * This object is used to represent the media in an IMS session. A media can be
 * seen as a pipe between the two endpoints where content can flow in either 
 * direction or both.
 * <p>
 * When a calling terminal creates a session, a number of media objects can be
 * added. When the session is started, the IMS engine creates a media
 * offer based on properties of the included media, and sends it to the remote
 * endpoint.
 * If the session is accepted, the remote endpoint has a sufficient amount of
 * information to allow it to create the corresponding media objects. In this 
 * way, both endpoints get the same view of the media transfer.
 * A common and useful scenario for IMS applications is to stream video and
 * audio to render in realtime. To support efficient implementations here, an
 * application can pass the stream to a platform-supplied standard 
 * <code>Player</code> that supports an appropriate common codec to do the 
 * rendering.
 * <p>
 * 
 * <code>Media</code> are subclassed based on the transport method used.
 * <p>
 * 
 * <h2>Content Types</h2>
 * Content types identifies the content type of a <code>Media</code>. They can
 * be registered MIME types or some user defined type that follow the MIME 
 * syntax. See RFC2045 and RFC2046.
 * <p>
 * Example content types:
 * <ul>
 * <li>"text/plain"</li>
 * <li>"image/png"</li>
 * <li>"video/mpeg"</li>
 * <li>"application/myFpsGame"</li>
 * </ul>
 * 
 * <h2>Media Life Cycle</h2>
 * The life cycle of each <code>Media</code> is independent of other
 * <code>Medias</code> life cycles and has four main states: 
 * <code>STATE_INACTIVE</code>, <code>STATE_PENDING</code>, 
 * <code>STATE_ACTIVE</code> and <code>STATE_DELETED</code>. There is also a 
 * fifth state, <code>STATE_PROPOSAL</code>, but it is not part of the ordinary
 * life cycle but instead only meant to track changes on a modified
 * <code>Media</code>.
 * <p>
 * The media life cycle is connected to the life cycle of the 
 * <code>Session</code>. A transitions in a <code>Media</code> is the effect
 * of a session transition in the form of a session callback or to a method
 * call in the session, as can be seen in <i>Figure 1</i>.
 * <p>
 * <img src="doc-files/media-1.png"><br>
 * <br>
 * <i><b>Figure 1:</b>The media states, except STATE_PROPOSAL</i><br>
 * <br>
 * 
 * <h3>Update state</h3>
 * The update state is used to track changes that has been done to an active
 * <code>Media</code> on either the originating or terminating endpoint.
 * <p>
 * When a <code>Media</code> has been removed or modified the 
 * <code>getUpdateState</code> reflect the changes but the modifications does
 * not take place before the <code>Session</code> has been negotiated and
 * accepted.
 * 
 * <h3>Examples of the Media states and update states</h3>
 * This example shows which state and update state the <code>Media</code> can 
 * reside in at the terminating endpoint when receiving a session invitation or
 * session update.
 * <p> 
 * <pre>
 * public void sessionInvitationReceived(CoreService service, Session session) {
 *   // all medias are pending in a session invite
 *   media.getState() == STATE_PENDING;
 *   // getUpdateState is not applicable in this state
 * }
 * ...
 * public void sessionUpdateReceived(Session session) {
 *   // this will show that the media is proposed to be added to the session
 *   media.getState() == STATE_PENDING;
 *   // getUpdateState is not applicable in this state
 *   ...
 *   // this will show that the media is proposed to be removed from the session
 *   media.getState() == STATE_ACTIVE;
 *   media.getUpdateState() == UPDATE_REMOVED;
 *   ...
 *   // this will show that the media is proposed to be modified in a session
 *   media.getState() == STATE_ACTIVE;
 *   media.getUpdateState() == UPDATE_MODIFIED;
 *   media.getProposal(); // returns a fictious media to track changes
 *   ...
 *   // this will show a media that is unchanged in the session update
 *   media.getState() == STATE_ACTIVE;
 *   media.getUpdateState() == UPDATE_UNCHANGED;
 * }
 * </pre>
 * <p>
 * This example shows how an application can inspect changes done to a media in
 * a session update.
 * <pre>
 * public void sessionUpdateReceived(Session session) {
 *   // if the first media in the array is a media in STATE_ACTIVE and
 *   // update state is UPDATE_MODIFIED
 *   
 *   Media currentMedia = session.getMedia[0];
 *   Media changedMedia = currentMedia.getProposal();
 *   
 *   // then we can track changes, for example
 *   if (currentMedia.getDirection() != changedMedia.getDirection()) {
 *      ...
 *   }
 *   // and now we can decide if the update should be accepted or rejected
 *   session.accept();
 * }
 * </pre>
 */
public interface Media {

    public enum Direction {
        INACTIVE, RECEIVE, SEND, SEND_RECEIVE
    }

    public enum State {
        /** The media has been accepted by both parties. */
        ACTIVE,
        /** The media is now deleted or rejected by one of the parties. */
        DELETED,
        /** The media offer still needs to be accepted by both parties. */
        INACTIVE,
        /** The media offer has been sent to the remote endpoint. */
        PENDING,
        /** */
        PROPOSAL
    }

    /** Returns the current direction of this Media. */
    Direction getDirection();


    /** Returns the current state of this Media. */
    State getState();

    /** Sets the direction of this Media. */
    void setDirection(Direction direction);
    
    /** Mark this media as to be deleted - or rejected. 
     *  If a  ImsMedia is changed in an established ImsSession, the application
     *  has the responsibility to call update on the ImsSession.
     */
    void delete();    
}
