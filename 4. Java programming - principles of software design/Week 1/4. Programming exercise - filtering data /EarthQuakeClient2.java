import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        // NOTE: apparently the Filter f argument can also be any object created by 
        // a class that implements Filter, like a MatchAllFilter object
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        
        /*
        Filter f = new MagnitudeFilter(4.0, 5.0);
        ArrayList<QuakeEntry> answer = filter(list, f);
        f = new DepthFilter(-35000.0, -12000.0);
        answer = filter(answer, f);
        for (QuakeEntry qe : answer) {
            System.out.println(qe);
        }
        */
       
       Location tokyo = new Location(35.42, 139.43);
       float maxDist = 10000000;  // 10,000,000 m or 10,000 km
       Filter f = new DistanceFilter(tokyo, maxDist);  
       ArrayList<QuakeEntry> answer = filter(list, f);
       f = new PhraseFilter("end", "Japan");
       answer = filter(answer, f);
       for (QuakeEntry qe : answer) {
           System.out.println(qe);
       }
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }
    
    public void testMatchAllFilter() {
        String source = "data/nov20quakedatasmall.atom";
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list = parser.read(source);
        
        /*
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
        */
       
        System.out.println("read data for "+list.size()+" quakes");
       
        MatchAllFilter maf = new MatchAllFilter();
        Filter f = new MagnitudeFilter(0.0, 2.0);
        maf.addFilter(f);
        f = new DepthFilter(-100000.0, -10000.0);
        maf.addFilter(f);
        f = new PhraseFilter("any", "a");
        maf.addFilter(f);
        
        ArrayList<QuakeEntry> answer = filter(list, maf);
        for (QuakeEntry qe : answer) {
            System.out.println(qe);
        }
        
        System.out.println("Filters used are: ");
        String filters = maf.getName();
        System.out.println(filters);
    }
    
    public void testMatchAllFilter2() {
        String source = "data/nov20quakedatasmall.atom";
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        
        MatchAllFilter maf = new MatchAllFilter();
        // Filter for magnitude between 0.0 and 3.0
        Filter f = new MagnitudeFilter(0.0, 3.0);
        maf.addFilter(f);
        // Filter for distance from Tulsa, Oklahoma less than 10000000 meters (10000 km)
        Location city = new Location(36.1314, -95.9372);
        f = new DistanceFilter(city, 10000000);
        maf.addFilter(f);
        // Filter for the substring “Ca” in the title
        f = new PhraseFilter("any", "Ca");
        maf.addFilter(f);
        
        ArrayList<QuakeEntry> answer = filter(list, maf);
        for (QuakeEntry qe : answer) {
            System.out.println(qe);
        }
    }
}
