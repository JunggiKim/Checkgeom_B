package io.dodn.springboot.core.api.service;


import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibrary;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryMoreViewType;
import io.dodn.springboot.core.api.service.response.LibraryServiceResponse;
import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class LibraryMoreViewLinkReader {

    private final GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;

    public LibraryMoreViewLinkReader(GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader) {
        this.gyeonggiDoCyberLibraryReader = gyeonggiDoCyberLibraryReader;
    }


    public List<String> getMoreViewLinks(String keyword, Element htmlBody) {
        List<GyeonggiDoCyberLibraryMoreViewType> moreViewList = gyeonggiDoCyberLibraryReader.isMoreViewList(htmlBody);

        boolean isMoreView = moreViewList.stream().anyMatch(GyeonggiDoCyberLibraryMoreViewType::isMoreView);
        List<String> moreViewLink = new ArrayList<>();
        if (isMoreView) {
            moreViewLink = moreViewList.stream()
                    .map(viewType -> GyeonggiDoCyberLibrary.moreViewSearchUrlCreate(keyword, viewType))
                    .toList();
        }
        return moreViewLink;
    }



}
