package com.createhaus.biscotti;

import java.util.HashMap;

public class BSession {

    static HashMap<String, Object> values = new HashMap();

    static public Object get(String name) {
        return values.get(name);
    }

    static public void set(String name, Object value) {
        values.put(name, value);
    }

}