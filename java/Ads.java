package com.kononovco.haircutselectionbyphoto;

public interface Ads {

    String getAdUnitId();

    void close();
    void load();
    void addListener();
}