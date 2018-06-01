package by.bsu.rfe.smsservice.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MessageUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);

  public static String createMessage(String template, Map<String, String> messageParameters) {
    String originalMessage = new String(template);
    String regex = "\\$\\{([^}]+)\\}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(template);
    String result = template;
    while (matcher.find()) {
      String token = matcher.group();
      String replacementValue = null;
      if (messageParameters.containsKey(token)) {
        replacementValue = messageParameters.get(token);
      } else {
        LOGGER.error("Not enough parameters. Could not create message.");
        LOGGER.error("Original message: {}", originalMessage);
        LOGGER.error("Parameters: {}", messageParameters);
        throw new IllegalArgumentException("Not enough parameters. Could not create message.");
      }

      result = result.replaceFirst(Pattern.quote(token), replacementValue);
    }

    return result;
  }
}
