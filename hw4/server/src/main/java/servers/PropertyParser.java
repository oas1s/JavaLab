package servers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PropertyParser {
    String path;
    Scanner fileReader;

    public PropertyParser(String path) {
        this.path = path;
        fileReader = new Scanner(this.path);
    }

    public Map<String, String> parse(){
        Map<String,String> map = new HashMap<>();
        while (fileReader.hasNext()) {
            String[] mas = fileReader.nextLine().split(": ");
            String arg1 = mas[0];
            String arg2 = mas[1];
            map.put(arg1, arg2);
        }
        return map;
    }
}
