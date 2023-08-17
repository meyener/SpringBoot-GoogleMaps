package com.springgmap.dao;

import com.springgmap.model.Restaurant;

import java.util.List;

public interface DAO<T> {
    List<Restaurant> list();
    void create(T t);
    T get(int id);
    //void update(T t, int id);
    void delete(int id);
}
