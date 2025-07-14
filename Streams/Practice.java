import java.util.*;
import java.util.stream.Collectors;

public class Practice {
    public static void main(String[] args) {
        // List<String> names = Arrays.asList("Alice", "Bob", "Amanda", "Brian");
        // List<String> res = names.stream().filter(a -> a.charAt(0) == 'A').collect(Collectors.toList());
        // System.out.println(res);

        List<String> words = Arrays.asList("hello", "world", "java");
        List<String> upperCaseWords = words.stream().map(String::toUpperCase).toList();
        System.out.println(upperCaseWords);


    }    
}
