package hungpt.utils;

import hungpt.constant.EntityCharacter;
import hungpt.constant.State;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlHelper {

    private static Character quote;
    private static final String[] IGNORE_TAGS = new String[]{"head", "noscript", "style", "iframe", "script"};
    private static final List<String> INLINE_TAGS = Arrays.asList("area", "base", "br", "col", "command",
            "embed", "hr", "img", "input", "keygen",
            "link", "meta", "param", "source", "track", "wbr");

    public static String getWellformHTML(String html) {
        return wellFormHtml(refineHtml(html));
    }


    //State machine wellform
    private static String wellFormHtml(String html) {
        char[] reader = html.toCharArray();

        StringBuilder writer = new StringBuilder();
        writer.append("<html>");
        boolean isOpenTag = false, isCloseTag = false, isEmptyTag = false;

        Stack<String> stack = new Stack<>();

        StringBuilder content = new StringBuilder();
        StringBuilder openTagName = new StringBuilder();
        StringBuilder closeTagName = new StringBuilder();

        StringBuilder attName = new StringBuilder();
        StringBuilder attValue = new StringBuilder();

        Map<String, String> attributes = new HashMap<>();
        State state = State.CONTENT;
        for (char el : reader) {
            switch (state) {
                case CONTENT:
                    if (el == EntityCharacter.LESS_THAN.getCharacter()) {
                        state = State.OPEN_BRACKET;
                        // Ex: Bộ GD&ĐT -> Bộ GD&amp;ĐT -> Wellform XML
                        writer.append(content.toString().trim().length() > 0 ? content.toString().trim().replaceAll("&", "&amp;").replaceAll("\t", "") : "");
                    } else {
                        content.append(el);
                    }
                    break;
                case OPEN_BRACKET:
                    isEmptyTag = false;
                    if (el == EntityCharacter.SLASH.getCharacter()) {
                        isOpenTag = false;
                        isCloseTag = true;
                        state = State.CLOSE_TAG_SLASH;
                    } else if (StringHelper.isStartCharacter(el)) {
                        isOpenTag = true;
                        isCloseTag = false;
                        openTagName.setLength(0);
                        openTagName.append(el);
                        state = State.OPEN_TAG_NAME;
                    }
                    break;
                case CLOSE_TAG_SLASH:
                    if (StringHelper.isStartCharacter(el)) {

                        closeTagName.setLength(0);
                        closeTagName.append(el);
                        state = State.CLOSE_TAG_NAME;
                    }
                    break;
                case CLOSE_TAG_NAME:
                    if (el == EntityCharacter.SPACE.getCharacter()) {
                        state = State.WAIT_END_TAG_CLOSE;
                    } else if (el == EntityCharacter.GREAT_THAN.getCharacter()) {
                        state = State.CLOSE_BRACKET;
                    } else if (StringHelper.isNameCharacter(el)) {
                        closeTagName.append(el);
                    }
                    break;
                case WAIT_END_TAG_CLOSE:
                    if (el == EntityCharacter.GREAT_THAN.getCharacter()) {
                        state = State.CLOSE_BRACKET;
                    }
                    break;
                case OPEN_TAG_NAME:
                    if (el == EntityCharacter.SPACE.getCharacter()) {
                        state = State.TAG_INNER;

                        attributes.clear();
                    } else if (el == EntityCharacter.SLASH.getCharacter()) {
                        state = State.EMPTY_SLASH;
                    } else if (el == EntityCharacter.GREAT_THAN.getCharacter()) {
                        state = State.CLOSE_BRACKET;
                    } else if (StringHelper.isNameCharacter(el)) {
                        openTagName.append(el);
                    }
                    break;
                case TAG_INNER:
                    if (StringHelper.isStartCharacter(el)) {
                        state = State.ATT_NAME;

                        // Init Area inner tag
                        attName.setLength(0);
                        attName.append(el);
                    } else if (el == EntityCharacter.GREAT_THAN.getCharacter()) {
                        state = State.CLOSE_BRACKET;
                    } else if (el == EntityCharacter.SLASH.getCharacter()) {
                        state = State.EMPTY_SLASH;
                    }
                    break;
                case ATT_NAME:
                    if (StringHelper.isNameCharacter(el)) {
                        attName.append(el);
                    } else if (el == EntityCharacter.SPACE.getCharacter()) {
                        state = State.EQUAL_WAIT;
                    } else if (el == EntityCharacter.EQUAL.getCharacter()) {
                        state = State.EQUAL;
                    } else {
                        if (el == EntityCharacter.SLASH.getCharacter()) {
                            attributes.put(attName.toString(), "");
                            state = State.EMPTY_SLASH;
                        } else if (el == EntityCharacter.GREAT_THAN.getCharacter()) {
                            attributes.put(attName.toString(), "");
                            state = State.CLOSE_BRACKET;
                        }
                    }
                    break;
                case EQUAL_WAIT:
                    if (el == EntityCharacter.EQUAL.getCharacter()) {
                        state = State.EQUAL;
                    } else
                        // 2 att and more in tag <a class="a" href="b" />
                        if (StringHelper.isStartCharacter(el)) {
                            attributes.put(attName.toString(), "");
                            attName.setLength(0);
                            attName.append(el);

                            state = State.ATT_NAME;
                        }
                    break;
                case EQUAL:
                    if (el == EntityCharacter.DOUBLE_QOUT.getCharacter() || el == EntityCharacter.SINGLE_QOUT.getCharacter()) {
                        quote = el;
                        state = State.ATT_VALUE_Q;
                        attValue.setLength(0);
                    } else if (el != EntityCharacter.SPACE.getCharacter() && el != EntityCharacter.GREAT_THAN.getCharacter()) {
                        state = State.ATT_VALUE_NQ;

                        attValue.setLength(0);
                        attValue.append(el);
                    }
                    break;
                case ATT_VALUE_Q:
                    if (el == quote) {
                        state = State.TAG_INNER;
                        attributes.put(attName.toString(), attValue.toString());
                    } else {
                        attValue.append(el);
                    }
                    break;
                case ATT_VALUE_NQ:
                    if (el != EntityCharacter.GREAT_THAN.getCharacter() && el != EntityCharacter.SPACE.getCharacter()) {
                        attValue.append(el);
                    } else if (el == EntityCharacter.SPACE.getCharacter()) {
                        state = State.TAG_INNER;
                        attributes.put(attName.toString(), attValue.toString());
                    } else if (el == EntityCharacter.GREAT_THAN.getCharacter()) {
                        state = State.CLOSE_BRACKET;
                        attributes.put(attName.toString(), attValue.toString());
                    }
                    break;
                case CLOSE_BRACKET:
                    String openTagLowerCase = openTagName.toString().toLowerCase();
                    String closeTagLowerCase = closeTagName.toString().toLowerCase();
                    if (isOpenTag) {
                        if (INLINE_TAGS.contains(openTagLowerCase)) {
                            isEmptyTag = true;
                        }
                        writer.append(EntityCharacter.LESS_THAN.getCharacter()).append(openTagLowerCase).append(convertMapToAttributesXML(attributes)).append(isEmptyTag ? EntityCharacter.SLASH.getCharacter() : "").append(EntityCharacter.GREAT_THAN.getCharacter());

                        attributes.clear();

                        if (!isEmptyTag) {
                            stack.push(openTagLowerCase);
                        }
                    } else if (isCloseTag) {
                        if (!stack.isEmpty() && stack.contains(closeTagLowerCase)) {
                            while ((!stack.isEmpty() && !stack.peek().equals(closeTagLowerCase))) {
                                writer.append(EntityCharacter.LESS_THAN.getCharacter()).append(EntityCharacter.SLASH.getCharacter()).append(stack.pop()).append(EntityCharacter.GREAT_THAN.getCharacter());
                            }
                            if ((!stack.isEmpty() && stack.peek().equals(closeTagLowerCase))) {
                                writer.append(EntityCharacter.LESS_THAN.getCharacter()).append(EntityCharacter.SLASH.getCharacter()).append(stack.pop()).append(EntityCharacter.GREAT_THAN.getCharacter());
                            }
                        }
                    }
                    if (el == EntityCharacter.LESS_THAN.getCharacter()) {
                        state = State.OPEN_BRACKET;
                    } else {
                        content.setLength(0);
                        content.append(el);
                        state = State.CONTENT;
                    }
                    break;
                case EMPTY_SLASH:
                    if (el == EntityCharacter.GREAT_THAN.getCharacter()) {
                        state = State.CLOSE_BRACKET;
                        isEmptyTag = true;
                    }
                    break;
            }
        }
        while (!stack.isEmpty()) {
            writer.append(EntityCharacter.LESS_THAN.getCharacter()).append(EntityCharacter.SLASH.getCharacter()).append(stack.pop()).append(EntityCharacter.GREAT_THAN.getCharacter());
        }
        return writer.append("</html>").toString();
    }

    public static String convertMapToAttributesXML(Map<String, String> attributes) {
        String result = "";
        StringBuilder keyValue = new StringBuilder();
        for (String key : attributes.keySet()) {
            keyValue.setLength(0);
            String value = attributes.get(key)
                    .replace("&", "&amp;")
                    .replace("\"", "")
                    .replace("\'", "")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");
            keyValue.append(EntityCharacter.SPACE.getCharacter()).append(key).append(EntityCharacter.EQUAL.getCharacter());
            keyValue.append(EntityCharacter.DOUBLE_QOUT.getCharacter()).append(value).append(EntityCharacter.DOUBLE_QOUT.getCharacter());

            result += keyValue.toString();
        }
        return result;

    }

    private static String refineHtml(String html) {
        html = getMainContent(html);
        html = removeNeedlessTags(html);
        return html;
    }

    private static String getMainContent(String src) {
        String result = src;
        Matcher matcher = Pattern.compile("<body.*?</body>").matcher(src);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return result;
    }

    private static String removeNeedlessTags(String src) {
        String result = src;

        String expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");
        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");
        for (String exp : IGNORE_TAGS) {
            expression = String.format("<%s.*?</%s>", exp, exp);
            result = result.replaceAll(expression, "");
        }

        return result;
    }
}
