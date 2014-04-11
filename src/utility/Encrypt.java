package utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.naming.ServiceUnavailableException;

import sun.misc.BASE64Encoder;

public class Encrypt {

	public synchronized static String encrypt(String plaintext) throws ServiceUnavailableException
	  {
	    MessageDigest md = null;
	    try
	    {
	      md = MessageDigest.getInstance("SHA"); 
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	      throw new ServiceUnavailableException(e.getMessage());
	    }
	    try
	    {
	      md.update(plaintext.getBytes("UTF-8")); 
	    }
	    catch(UnsupportedEncodingException e)
	    {
	      throw new ServiceUnavailableException(e.getMessage());
	    }

	    byte raw[] = md.digest();
	    String hash = (new BASE64Encoder()).encode(raw); 
	    return hash; 
	  }
	
	
}
