package com.shchek.pets.dto.client;

import lombok.Data;

import java.util.List;

@Data
public class NewsItemDto {

    String by;

    List<Long> kids;

    Long score;

    String title;

    String type;

    String url;
}
