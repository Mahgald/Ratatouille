package unitTest;


import junit.framework.TestCase;
import model.FriendRequest;
import model.User;

public class FriendRequestTest extends TestCase {

	private FriendRequest friendRequest;
	private User sender,receptor;
	
	protected void setUp(){
		sender = new User();
		receptor = new User();
		friendRequest = new FriendRequest(sender, receptor);
	}
	/**
	 * testea salida de los getter
	 */
	public void testGetter(){
		assertEquals(sender, friendRequest.getSender());
		assertEquals(receptor, friendRequest.getRecepter());
	}
	/**
	 * testea que la confirmacion 
	 */
	public void testConfirm(){
//		friendRequest.confirm();
		friendRequest.confirm(receptor);
		assertTrue(friendRequest.isRequestStatus());
	}
	/**
	 * testea el rechazp
	 */
	public void testReject(){
//		friendRequest.reject();
		friendRequest.reject(receptor);
		assertFalse(friendRequest.isRequestStatus());
	}
	/**
	 * testea si acepto la solicitud la lista de amigos se incrementa
	 */
	public void testFriendSize(){
//		friendRequest.confirm();
		friendRequest.confirm(receptor);
		assertEquals(1,sender.getFriends().size());
		assertEquals(1, receptor.getFriends().size());
	}
	/**
	 * testea si confirmo la solicitud que esten en la lista de amigos de ambos
	 */
	public void testIncludeFriend(){
//		friendRequest.confirm();
		friendRequest.confirm(receptor);
		assertTrue(sender.getFriends().contains(receptor));
		assertTrue(receptor.getFriends().contains(sender));
	}
	/**
	 * testea si rechazo la solicutud que no esten en la lista de amigos en ambos
	 */
	public void testNoIncludeFriend(){
//		friendRequest.reject();
		friendRequest.reject(receptor);
		assertFalse(sender.getFriends().contains(receptor));
		assertFalse(receptor.getFriends().contains(sender));
	}
}
