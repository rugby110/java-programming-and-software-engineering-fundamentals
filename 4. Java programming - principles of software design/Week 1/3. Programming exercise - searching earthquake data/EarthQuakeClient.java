import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     *  Returns an ArrayList of type QuakeEntry of all the earthquakes from quakeData 
     *  that have a magnitude larger than magMin.
     */
    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // For each quake in quakeData
        for (QuakeEntry qe : quakeData) {
            // If quake's magnitude is larger than magMin, add to answer
            if (qe.getMagnitude() > magMin) {
                answer.add(qe);
            }
        }
        return answer;
    }
    
    /**
     * Returns all the earthquakes from quakeData that are less than distMax 
     * from the location from.
     */
    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax, Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // For each quake in quakeData
        for (QuakeEntry qe : quakeData) {
            // Get distance between quake and from
            Location qeLoc = qe.getLocation();
            float dist = from.distanceTo(qeLoc);
            if (dist < distMax) {
                answer.add(qe);
            }
        }
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }
    
    /**
     * Prints earthquakes above a certain magnitude, and the number of such earthquakes.
     */
    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        double mag = 5.0;
        ArrayList<QuakeEntry> bigOnes = filterByMagnitude(list, mag);
        System.out.println("Reading data for " + list.size() + " quakes to find those that were above" +
                            " magnitude " + mag);
        for (QuakeEntry qe : bigOnes) {
            System.out.println(qe);
        }
        System.out.println("Found " + bigOnes.size() + " quakes that fit that criteria");
    }
    
    /**
     *  Prints the distance from each earthquake found to the specified city, 
     *  followed by the information about the city. Distance caps at a specified amount.
     */
    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        
        float distMax = 1000000;  // meters

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);
        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);
        ArrayList<QuakeEntry> quakes = filterByDistanceFrom(list, distMax, city);
        for (QuakeEntry qe : quakes) {
            float dist = city.distanceTo(qe.getLocation());
            // Convert dist to kilometers
            dist = dist / 1000;
            System.out.println(dist + " " + qe.getInfo());
        }
        System.out.println("Found " + quakes.size() + " that match that criteria");
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
    /**
     *  Returns all the earthquakes from quakeData whose depth is between 
     *  minDepth and maxDepth, exclusive.
     */
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            double qeDepth = qe.getDepth();
            if (qeDepth > minDepth && qeDepth < maxDepth) {
                answer.add(qe);
            }
        }
        return answer;
    }
    
    /**
     * Uses filterByDepth to print all the earthquakes from a data source whose 
     * depth is between a given minimum and maximum value.
     */
    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        double minDepth = -10000.0;
        double maxDepth = -5000.0;
        ArrayList<QuakeEntry> quakes = filterByDepth(list, minDepth, maxDepth);
        System.out.println("read data for " + list.size() + " quakes");
        for (QuakeEntry quake : quakes) {
            System.out.println(quake);
        }
        System.out.println("Found " + quakes.size() + " that match that criteria");
    }
    
}
