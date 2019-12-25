package com.zab.netty.protal.idwork;

public interface WorkerIdStrategy {
    void initialize();

    long availableWorkerId();

    void release();
}
