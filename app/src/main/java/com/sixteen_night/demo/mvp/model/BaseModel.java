package com.sixteen_night.demo.mvp.model;

import java.util.List;

public interface BaseModel<T,V> {
    void saveData(List<T> data);
    void loadDataFromCache(V listener);
}
