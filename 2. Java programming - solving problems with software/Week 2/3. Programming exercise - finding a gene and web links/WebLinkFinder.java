
/**
 * WebLinkFinder reads the lines from the file at this URL location, 
 * http://www.dukelearntoprogram.com/course2/data/manylinks.html, 
 * and prints each URL on the page that is a link to youtube.com.
 * 
 * @author Brienna Herold
 * @version Oct. 1, 2016
 */
import edu.duke.*;

public class WebLinkFinder {
    public void checkURL() {
        URLResource res = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for (String word : res.words()) {
            int youtubeIndex = word.toLowerCase().indexOf("youtube.com");
            if (youtubeIndex != -1) {
                /* If word contains "youtube.com", find the double quote to the left 
                 * and right of it to identify the URL
                 */
                int left = word.lastIndexOf("\"", youtubeIndex);
                int right = word.indexOf("\"", youtubeIndex);
                System.out.println(word.substring(left + 1, right));
            }
        }
    }
}
