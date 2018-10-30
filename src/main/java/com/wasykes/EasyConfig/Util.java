package com.wasykes.EasyConfig;

class Util {

    static boolean IsNotNull(Object... args) {
        for (Object o : args) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }
}
