package net.thinklog.common.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author azhao
 */
public class DiyExceptionChainHandler {

    private final List<DiyExceptionHandler> list = new ArrayList<>();

    public void set(DiyExceptionHandler e) {
        list.add(e);
    }

    public List<DiyExceptionHandler> get() {
        return list;
    }

}
