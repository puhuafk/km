package com.stylefeng.guns.modular.system.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Student {
    private int no;
    private String name;
    private int age;
    private String grage;
    public Student(int no, String name, int age, String grage) {
        super();
        this.no = no;
        this.name = name;
        this.age = age;
        this.grage = grage;
    }


}