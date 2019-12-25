package com.zab.netty.protal.idwork;

public interface RandomCodeStrategy {
    void init();

    int prefix();

    int next();

    void release();
}
