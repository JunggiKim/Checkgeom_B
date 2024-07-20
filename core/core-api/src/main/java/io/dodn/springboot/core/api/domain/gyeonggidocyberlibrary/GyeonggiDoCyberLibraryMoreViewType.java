package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

public record GyeonggiDoCyberLibraryMoreViewType(
        GyeonggiDoCyberLibraryBookType bookType
) {


    public static GyeonggiDoCyberLibraryMoreViewType of (GyeonggiDoCyberLibraryBookType bookType) {
            return new GyeonggiDoCyberLibraryMoreViewType(
                    bookType
            );
    }


}
