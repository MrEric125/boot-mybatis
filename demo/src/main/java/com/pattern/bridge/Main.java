package com.pattern.bridge;

/**
 * @author John·Louis
 * @date created on 2020/3/12
 * description:
 */
public class Main {

    public static void main(String[] args) {
        Implementor imple=new ConcreteA();
        Abstraction abs=new RefinedAbstraction(imple);
        abs.operation();
    }
}
