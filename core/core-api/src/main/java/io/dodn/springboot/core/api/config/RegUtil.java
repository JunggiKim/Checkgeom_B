package io.dodn.springboot.core.api.config;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegUtil {

    private static final String htmlTagDeletePattern = "<[^>]*>";

    public static String deleteHtmlTag(String target) {

        return target.replaceAll(htmlTagDeletePattern, "").replaceAll("&quot;", "");
    }

}
