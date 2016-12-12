
/**
 * Filters earthquakes for those that have depths between the 
 * minimum and maximum, inclusive.
 * 
 * @author Brienna Herold
 * @version Dec. 11, 2016
 */
public class DepthFilter implements Filter {
    private double minDepth;
    private double maxDepth;
    private String name;
    
    public DepthFilter(double num1, double num2) {
        minDepth = num1;
        maxDepth = num2;
        name = "DepthFilter";
    }
    
    public boolean satisfies(QuakeEntry qe) {
        double depth = qe.getDepth();
        if (depth >= minDepth && depth <= maxDepth) {
            return true;
        }
        return false;
    }
    
    public String getName() {
        return name;
    }
}

