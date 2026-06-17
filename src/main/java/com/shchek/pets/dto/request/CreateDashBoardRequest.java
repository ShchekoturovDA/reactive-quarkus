package com.shchek.pets.dto.request;

import io.smallrye.common.constraint.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateDashBoardRequest {

    @NotNull
    String dashBoardName;

    List<FilterDTO> filters;

    Long dateFrom;

    Long dateEnd;
}
