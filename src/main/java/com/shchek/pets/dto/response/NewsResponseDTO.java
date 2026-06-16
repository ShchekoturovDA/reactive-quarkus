package com.shchek.pets.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDTO {

    String title;
    String author;
    String url;

    public String toString() {
        return getTitle() + ": " + getUrl() + " by " + getAuthor() + "\n";
    }
}
