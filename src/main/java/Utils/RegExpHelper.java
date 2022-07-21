package Utils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpHelper {

    public static Optional<String> extractData(String text, String regularExpression){
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()){
            return Optional.of(matcher.group(1));
        }else {
            return Optional.empty();
        }
    }

    public static Optional<Matcher> extractDataMatcher(String text, String regularExpression){
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(text);

        if(matcher.find()){
            return Optional.of(matcher);
        }else {
            return Optional.empty();
        }
    }
}
