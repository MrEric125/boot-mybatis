package com.pattern.jpa;

/**
 * @author jun.liu
 * @date created on 2020/7/18
 * description:
 */
public interface IJpaService extends IBaseService {

    String findByName(String name);

}