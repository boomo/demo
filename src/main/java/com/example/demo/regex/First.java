package com.example.demo.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class First {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher("123");
        if (matcher.matches())
            System.out.println(true);
    }
}
