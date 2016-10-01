import edu.duke.*;

public class HelloWorld {
	public void runHello () {
		//FileResource res = new FileResource("hello_unicode.txt");
		URLResource res = new URLResource("http://www.dukelearntoprogram.com/java/hello_unicode.txt");
		for (String line : res.lines()) {
			System.out.println(line);
		}
	}
}
