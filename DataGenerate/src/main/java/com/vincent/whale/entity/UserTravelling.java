package com.vincent.whale.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Vincent on 2018/1/25.
 */
@ToString
public class UserTravelling {
    @Getter @Setter
    User user;

    @Getter @Setter
    Travelling travelling;
}
