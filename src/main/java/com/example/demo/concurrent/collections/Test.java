package com.example.demo.concurrent.collections;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Test {
    public static void main(String[] args) {
        ConcurrentHashMap map = new ConcurrentHashMap();
        /*高效并发的linkedList*/
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        Map m = Collections.synchronizedMap(new HashMap<>());
        Maps.newConcurrentMap();
        /*map、queue，List*/

    }

}
