package com.eduapi.edusystem.classes.classDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class ClassDTO {
    @Data
    @Builder
    @AllArgsConstructor
    public static class PostInput{
        String name;
        Long places;
        Long availablePlaces;
    }
    @Data
    @Builder
    @AllArgsConstructor
    public static class GetInput{
        String name;
        Long places;
        Long availablePlaces;
    }

}
