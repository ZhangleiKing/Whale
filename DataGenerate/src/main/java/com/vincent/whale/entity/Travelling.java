package com.vincent.whale.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Vincent on 2018/1/24.
 */
@ToString
public class Travelling {
    @Getter  @Setter
    private String departure;

    @Getter @Setter
    private String destination;

    @Getter @Setter
    private double consumption;

    @Getter @Setter
    private long createTime;
}
