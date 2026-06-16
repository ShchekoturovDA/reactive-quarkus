package com.shchek.pets.dto.request;

import io.smallrye.common.constraint.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateDashBoardRequest {

    @NotNull
    String name;

    List<FilterDTO> filters;

    Long dateFrom;

    Long dateEnd;
}
