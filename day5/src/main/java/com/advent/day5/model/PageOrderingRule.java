package com.advent.day5.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageOrderingRule {

    private Integer firstPage;
    private Integer secondPage;
}
