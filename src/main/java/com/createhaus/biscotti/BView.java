package com.createhaus.biscotti;

import java.awt.Component;
import java.util.List;

public interface BView {
    void close();
    void error(List<BError> errors);
    void open();
    void compositeView(String name, Component view);
}
