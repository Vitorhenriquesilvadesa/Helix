package org.vtko.helix;

import java.util.List;
import java.util.Map;

public class HelixClass implements HelixCallable {
    private final Map<String, HelixFunction> methods;
    final String name;
    final HelixClass superclass;

    HelixClass(String name, HelixClass superclass, Map<String, HelixFunction> methods) {
        this.name = name;
        this.methods = methods;
        this.superclass = superclass;
    }

    @Override
    public String toString() {
        return "<class " + this.name + ">";
    }

    @Override
    public int arity() {
        HelixFunction initializer = findMethod("constructor");
        if (initializer == null) return 0;
        return initializer.arity();    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        HelixInstance instance = new HelixInstance(this);
        HelixFunction initializer = findMethod("constructor");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }

    HelixFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        if (superclass != null) {
            return superclass.findMethod(name);
        }

        return null;
    }
}
