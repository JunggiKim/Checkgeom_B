package io.dodn.springboot.core.api.domain;

public record MoreView (
       boolean moreView,
       int totalCount
){

    public static MoreView of (
            boolean moreView,
            int totalCount
    ) {

        return new MoreView(
                moreView,
                totalCount
        );
    }

    public static boolean isMoreView(int totalCount) {
        return totalCount >= 10;
    }

}
