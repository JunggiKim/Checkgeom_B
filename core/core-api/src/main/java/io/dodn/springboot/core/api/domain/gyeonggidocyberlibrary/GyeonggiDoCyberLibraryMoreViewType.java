package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

public record GyeonggiDoCyberLibraryMoreViewType(
        GyeonggiDoCyberLibraryBookType bookType ,
        int totalCount
) {


    public static GyeonggiDoCyberLibraryMoreViewType of (GyeonggiDoCyberLibraryBookType bookType , int totalCount) {
        return new GyeonggiDoCyberLibraryMoreViewType(
                bookType,
                totalCount
        );
    }


    public boolean isMoreView () {
        return this.totalCount > 6 ;
    }

    public boolean isNotMoreView () {
        return this.totalCount <= 6 ;
    }


}
