package com.shchek.pets.dto.request;

import lombok.Data;

@Data
public class FilterDTO {

    String filterName;

    Boolean isReverse;

    String fileType;
}
