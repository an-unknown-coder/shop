package com.qf.config;

import javax.servlet.http.Cookie;
import java.io.Serializable;

public class SerializableCookie extends Cookie implements Serializable {
    public SerializableCookie(String name, String value) {
        super(name, value);
    }
}
