package hungpt.utils;

import hungpt.constant.EntityCharacter;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringHelper {

    public static String unAccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public static boolean isNameCharacter(char c) {
        return Character.isLetterOrDigit(c) || c == EntityCharacter.UNDERSCORE.getCharacter()
                || c == EntityCharacter.HYPHEN.getCharacter() || c == EntityCharacter.PERIOD.getCharacter();
    }

    public static boolean isStartCharacter(char c) {
        return Character.isLetter(c) || c == EntityCharacter.COLON.getCharacter() || c == EntityCharacter.UNDERSCORE.getCharacter();
    }


    public static double getValidWattage(String str) {
        double result = 0;
        if (!str.equals("0")) {
            if (str.split(":").length == 2) {
                str = str.split(":")[1].trim().replaceAll("&agrave;", "à").replaceAll("~", "").replaceAll("&iacute;", "í").replaceAll("([0-9])+kg", "");
            }
            if (str.contains("-")) {
                str = str.split("-")[1];
            }
            if (str.contains("/")) {
                str = str.split("/")[1];
            }
            result = getContainsNumber(str);
        }
        return result;
    }

    private static double getContainsNumber(String str) {
        char[] strArr = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < strArr.length - 1; i++) {
            char c = strArr[i];
            if (Character.isDigit(c) || c == EntityCharacter.PERIOD.getCharacter()) {
                sb.append(c);
                count++;
            }
        }
        double num = 0;
        if (count > 0) {
            try {
                num = Float.parseFloat(sb.toString());
            } catch (Exception e){
                return 0;
            }
            if (str.toLowerCase().contains("kw/h")) {
                num = num * 1000;
            } else if (str.toLowerCase().contains("kw/ngày")) {
                num = num * 1000 / 24;
            } else if (str.toLowerCase().contains("lít/giờ")) {
                // 1 lit ~ 3 W
                num = num * 3;
            } else if (str.toLowerCase().contains("btu")) {
                //1 BTU ~ 0.3 W
                num = Float.parseFloat(sb.toString().replace(".", "")) * 0.3;
            }
        }
        return num;
    }
}
