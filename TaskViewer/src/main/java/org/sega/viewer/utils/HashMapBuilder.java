package org.sega.viewer.utils;

import java.util.*;

/**
 * @author: Raysmond
 *
 * A helper class for initializing HashMap
 */
public class HashMapBuilder<K,V> {

    private Map<K,V> map = new HashMap<>();

    public HashMapBuilder(){

    }

    public HashMapBuilder<K,V> put(K k,V v){
        map.put(k,v);
        return this;
    }

    public Map<K,V> build(){
        return map;
    }
}
