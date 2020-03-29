package com.example.Spring.Security.Tutorial;

public class RegularPatternTester {
    public static void main(String[] args) {
        doSomething("Kshitij");

        System.out.println("Done!");
    }

    public static void doSomething(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            System.out.println("Hello " + strings[i]);
        }
    }
}
