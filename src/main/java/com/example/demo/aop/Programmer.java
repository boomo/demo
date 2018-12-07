package com.example.demo.aop;

public class Programmer implements IPeople{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Programmer(String name) {
        this.name = name;
    }

    @Override
    public void work() {
        System.out.println(name + " coding");
    }

    @Override
    public void meditating(){
        System.out.println("how does this framework work?");
    }

    @Override
    public void sport() {
        System.out.println("running and play badminton");
    }
}
