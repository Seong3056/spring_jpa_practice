package com.spring.jpa.chpa05_practice.dto;

import lombok.*;

import java.lang.reflect.Constructor;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PageDTO {

    private int page;
    private int size;

    public PageDTO() {
        this.page = 1;
        this.size = 10;
    }
}
