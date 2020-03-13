package com.pattern.abstractfactory;

/**
 * @author John·Louis
 * @date created on 2020/3/9
 * description:
 */
public class Product1Factory extends AbstractFactory {
    @Override
    public Product newProduct() {
        return new ConcreteProduct1();
    }
}
