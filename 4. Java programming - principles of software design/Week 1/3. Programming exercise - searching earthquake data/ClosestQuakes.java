
/**
 * Find N-closest quakes
 * 
 * @author Duke Software/Learn to Program
 * @version 1.0, November 2015
 */

import java.util.*;

public class ClosestQuakes {
    /**
     *  Returns the closest number of howMany earthquakes to the current Location.
     */
    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, 
    int howMany) {
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        // Fill ret with first howMany quakes from quakeData
        for (int i = 0; i < howMany; i++) {
            ret.add(quakeData.get(i));
        }
        // For each quake in quakeData
        for (QuakeEntry qe : quakeData) {
            // Calculate distance between quakeData quake and current location
            Location qeLoc = qe.getLocation();
            float distQe = current.distanceTo(qeLoc);
            System.out.println("Distance between quakeData quake and current location: " + distQe);
            // For each quake in ret,
            for (QuakeEntry quake : ret) {
                 // Calculate distance between ret quake and current location
                 Location qeRet = quake.getLocation();
                 float distRet = current.distanceTo(qeRet);
                 System.out.println("Distance between ret quake and current location: " + distRet);
                 // If quakeData quake is closer than ret quake
                 if (distQe < distRet) {
                     // Replace ret quake with quakeData quake
                     int index = ret.indexOf(quake);
                     ret.remove(index);
                     ret.add(index, qe);
                     System.out.println(distRet + " " + distQe);
                     System.out.println("Added quake to pos " + index);
                     // Check next quakeData quake
                     break;
                 }
            }
        }
        System.out.println(ret.size());
        return ret;
    }

    public void findClosestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());

        Location jakarta  = new Location(-6.211,106.845);

        ArrayList<QuakeEntry> close = getClosest(list,jakarta,3);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }
    
}
