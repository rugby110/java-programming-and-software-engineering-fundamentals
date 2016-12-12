
/**
 * Filters earthquakes that contain the given phrase 
 * at the given location in the title.
 * The location can be (“start”, ”end”, or “any”).
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhraseFilter implements Filter {
    private String where;
    private String phrase;
    
    public PhraseFilter(String s, String s2) {
        where = s;
        phrase = s2;
    }
    
    public boolean satisfies(QuakeEntry qe) {
        String title = qe.getInfo();
        if (where.equals("start") && title.startsWith(phrase)) {
            return true;
        } else if (where.equals("end") && title.endsWith(phrase)) {
            return true;
        } else if (where.equals("any") && title.contains(phrase)) {
            return true;
        } else {
            return false;
        }
    }
    
    public String getName() {
        return "PhraseFilter";
    }
}
