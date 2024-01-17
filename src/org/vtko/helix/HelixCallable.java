package org.vtko.helix;

import java.util.List;
import java.lang.FunctionalInterface;

public interface HelixCallable {
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);

    abstract class Native implements HelixCallable {
        String name;

        Native(String name) {
            this.name = name;
        }

        @Override
        public String toString(){
            return "<native_function: " + this.name + ">";
        }
    }
}
