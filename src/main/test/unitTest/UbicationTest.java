package unitTest;


import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;
import model.Ubication;

public class UbicationTest extends TestCase{
	


	
	
	
	
	
	
	
	private Ubication ubication,ubic2,ubic3;
	public void setUp(){
		ubication = new Ubication(-34.924265,-57.937067); 
		ubic2 = new Ubication( -34.929825,-57.943289); 
		ubic3 = new Ubication(  -34.933484,-57.947624); 
	}
	public void testCreateUbication() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		assertNotNull(ubication);
		//this.compareMoreThan1Km(ubication);
		assertTrue( ubication.compareMoreThan1Km(ubic2));
		assertFalse( ubication.compareMoreThan1Km(ubic3));
		
		/*ubic2.m();*/
	}
}
