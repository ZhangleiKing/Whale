package com.vincent.whale.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Vincent on 2018/1/24.
 */
@ToString
public class User implements Serializable{
    private static final long serialVersionUID = 1000001L;

    @Getter @Setter
    private String userName;

    @Getter  @Setter
    private int userAge;

    @Getter @Setter
    //0: male; 1: female
    private byte userSex;

    @Getter @Setter
    private String homeAddress;

    @Getter @Setter
    private String idCardNumber;

    @Getter  @Setter
    private String userId;
}
