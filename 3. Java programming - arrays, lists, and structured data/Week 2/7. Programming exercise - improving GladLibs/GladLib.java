import edu.duke.*;
import java.util.*;

public class GladLib {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> wordsUsed;
    private ArrayList<String> categoriesUsed;
    private Random myRandom;
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLib(){
        myMap = new HashMap<String, ArrayList<String>>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
        wordsUsed = new ArrayList<String>();
        categoriesUsed = new ArrayList<String>();
    }
    
    public GladLib(String source){
        myMap = new HashMap<String, ArrayList<String>>();
        initializeFromSource(source);
        myRandom = new Random();
        wordsUsed = new ArrayList<String>();
        categoriesUsed = new ArrayList<String>();
    }
    
    private void initializeFromSource(String source) {
        String[] categories = new String[9];
        categories[0] = "adjective";
        categories[1] = "noun";
        categories[2] = "color";
        categories[3] = "country";
        categories[4] = "name";
        categories[5] = "animal";
        categories[6] = "timeframe";
        categories[7] = "verb";
        categories[8] = "fruit";
        
        for (int i = 0; i < categories.length; i++) {
            String category = categories[i];
            ArrayList<String> words = readIt(source+"/"+category+".txt");
            myMap.put(category, words);
        }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (myMap.containsKey(label)) {
            ArrayList<String> words = myMap.get(label);
            return randomFrom(words);
        } else if (label.equals("number")) {
			return ""+myRandom.nextInt(50)+5;
		} else {
		    return "**UNKNOWN**";
		}
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String category = (w.substring(first+1,last));
        if (!categoriesUsed.contains(category)) {
            categoriesUsed.add(category);
        }
        String sub = getSubstitute(category);
        while (wordsUsed.contains(sub)) {
            sub = getSubstitute(w.substring(first+1, last));
        }
        wordsUsed.add(sub);
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public void makeStory(){
        wordsUsed.clear();
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println();
        System.out.println();
        System.out.println("Total number of words that were replaced: " + wordsUsed.size());
        totalWordsInMap();
        totalWordsConsidered();
    }
    
    private void totalWordsInMap() {
        int total = 0;
        for (String category : myMap.keySet()) {
            total += myMap.get(category).size();
        }
        System.out.println("The total number of words that were possible to pick from: " + total);
    }
    
    private void totalWordsConsidered() {
        int i = 0;
        int total = 0; 
        while (i < categoriesUsed.size()) {
            String category = categoriesUsed.get(i);
            if (!category.equals("number")) {
                total += myMap.get(category).size();
            }
            i++;
        }
        System.out.println("This GladLib used " + categoriesUsed.size() + " categories.");
        System.out.println("The total number of words in these categories that were " +
            "possible to pick from: " + total);
    }

}
