package io.dodn.springboot.core.api.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//경기도사이버도서관 또 하나의  검색 추가용
public record SearchBookType(String bookType, Long searchCount) {
    public static SearchBookType extraction(String html) {
        String searchCount = html.replaceAll(".*<span>(\\d+)</span>.*", "$1");
        String bookType = getBookType(html);

        return new SearchBookType(bookType, Long.valueOf(searchCount));
    }

    private static String getBookType(String html) {
        String regex = "<em>(.*?)\\s+\\(";
        Matcher matcher = Pattern.compile(regex).matcher(html);

        String bookType = "";
        if (matcher.find()) {
            bookType = matcher.group(1);
        }
        return bookType;
    }

    public boolean isMoreView() {
        return this.searchCount > 6;
    }

}
