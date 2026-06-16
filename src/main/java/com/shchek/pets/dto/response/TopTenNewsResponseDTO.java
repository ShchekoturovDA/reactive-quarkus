package com.shchek.pets.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopTenNewsResponseDTO {

    String title;
    String url;

    public String toString() {
        return getTitle() + ": " + getUrl() + "\n";
    }
}
