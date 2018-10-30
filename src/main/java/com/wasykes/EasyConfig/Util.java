package com.wasykes.EasyConfig;

/**
 *
 * General library utility class. (Subject to removal int he future)
 *
 * @author Darknuju
 * @version 1.0
 * @since 10/30/2018
 *
 */
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
