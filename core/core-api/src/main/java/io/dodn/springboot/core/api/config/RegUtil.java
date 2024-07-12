package io.dodn.springboot.core.api.config;


import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegUtil {

    public static String deleteHtmlTag(String target) {
        final String htmlTagDeletePattern = "<[^>]*>";

        return target.replaceAll(htmlTagDeletePattern, "")
                .replaceAll("&quot;", "");
    }


}
