package com.co.istad.piseth.spring_web_mvc.features.fileupload.dto;

import lombok.Builder;

@Builder
public record FileResponse(
        String name,
        String fileExt,
        Long size,
        String mediaType,
        String uri
) {
}
