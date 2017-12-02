import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @class
 * Reads the content of a url
 */

public class UrlContentReader {

	/**
	 *read the full content of the site with html tags
	 *@return String text
	 */

    public String allContent(String url) {

		try{

      		URL website = new URL(url);
        	URLConnection connection = website.openConnection();
        	BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String text = "";
        	String line;

    		    while ((line = in.readLine()) != null) {
    		        text += line.toString();
				}
        	in.close();
        	return text.toString();
		}catch(Exception e){
    		return null;
    	}
	}
}