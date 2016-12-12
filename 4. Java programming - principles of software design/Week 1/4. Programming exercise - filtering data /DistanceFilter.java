
/**
 * Filters earthquakes for those that are within the specified 
 * maximum distance from a given location.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DistanceFilter implements Filter {
    private Location givenLoc;
    private float maxDist;
    
    public DistanceFilter(Location loc, float max) {
        givenLoc = loc;
        maxDist = max;
    }
    
    public boolean satisfies(QuakeEntry qe) {
        Location quakeLoc = qe.getLocation();
        float dist = givenLoc.distanceTo(quakeLoc);
        if (dist < maxDist) {
            return true;
        }
        return false;
    }
    
    public String getName() {
        return "DistanceFilter";
    }
}
