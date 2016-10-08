
/**
 * WebLinkFinder reads the lines from a URL and does the following: 
 * prints the URLs,
 * prints the number of URLs found,
 * prints the the number of secure links found,
 * prints the number of links that have “.com” in them,
 * prints the number of links that end with “.com” or “.com/” and
 * print the total number of “.” that appear in all the links.
 * 
 * @author Brienna Herold
 * @version Oct. 7, 2016
 */
import edu.duke.*;

public class WebLinkFinder {
    public StorageResource findLinks(String url) {
        URLResource res = new URLResource(url);
        StorageResource links = new StorageResource();
        
        // Iterate over each "word" in the file at the URL
        for (String word : res.words()) {
            // Find a link, also checking that it begins with "http" to avoid storing internal links
            int index = word.indexOf("href=\"http");
            // If a link is found,
            if (index != -1) {
                int start = word.indexOf("\"", index);
                int end = word.indexOf("\"", start + 1);
                String link = word.substring(start + 1, end);
                // Store the link
                links.add(link);
            }
        }
        
        return links;
    }
    
    public void testURLWithStorage() {
        StorageResource links = findLinks("http://www.dukelearntoprogram.com/course2/data/newyorktimes.html");
        
        // Print number of links found
        System.out.println(links.size() + " links found");
        
        // Analyze the links
        int secureLinks = 0;
        int comLinks = 0;
        int endsWithCom = 0;
        int periods = 0;
        for (String link : links.data()) {
            // Print each link
            System.out.println(link);

            // Determine if link is secure
            if (link.indexOf("https:") != -1) {
                secureLinks++;
            }
            
            // Determine if link contains ".com"
            int comIndex = link.indexOf(".com");
            if (comIndex != -1) {
                comLinks++;
                // Determine if link ends with ".com or ".com/"
                if (link.endsWith(".com") || link.endsWith(".com/")) {
                    endsWithCom++;
                }
            }
            
            // Track all periods that appear in the links
            int loc = 0;
            while (loc != -1) {
                loc = link.indexOf(".", loc);
                if (loc != -1) {
                    periods++;
                    loc += 1;
                }
            }
        }
        
        System.out.println("Number of secure links found: " + secureLinks);
        System.out.println("Number of links that have \".com\" in the link: " + comLinks);
        System.out.println("Number of links that end with \".com\" or \".com/\": " + endsWithCom);
        System.out.println("Number of periods in all the links: " + periods);
    }
}
