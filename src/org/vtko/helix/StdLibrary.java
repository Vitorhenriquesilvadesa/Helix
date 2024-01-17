package org.vtko.helix;

import java.util.List;

public final class StdLibrary {
    private StdLibrary() {}

    static void defineNativeFunctions(Environment globals) {
        globals.define("clock", new HelixCallable.Native("clock") {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter,
                               List<Object> arguments) {
                return (float) System.currentTimeMillis() / 1000.0;
            }
        });

        globals.define("print", new HelixCallable.Native("print") {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter,
                               List<Object> arguments) {

                System.out.print(stringify(arguments.getFirst()));
                return null;
            }
        });

        globals.define("println", new HelixCallable.Native("println") {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter,
                               List<Object> arguments) {

                System.out.println(stringify(arguments.getFirst()));
                return null;
            }
        });

        globals.define("read", new HelixCallable.Native("read") {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter,
                               List<Object> arguments) {

                java.util.Scanner scanner = new java.util.Scanner(System.in);
                return scanner.nextLine();
            }
        });

        globals.define("prompt", new HelixCallable.Native("prompt") {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter,
                               List<Object> arguments) {

                System.out.print(stringify(arguments.getFirst()));
                java.util.Scanner scanner = new java.util.Scanner(System.in);
                return scanner.nextLine();
            }
        });

        globals.define("int", new HelixCallable.Native("int") {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter,
                               List<Object> arguments) {

                float result;

                try {
                    result = Float.parseFloat(arguments.getFirst().toString());
                } catch (NumberFormatException exception) {
                    throw new TypeCastException("Can't convert value to int.");
                }

                return result;
            }
        });
    }

    private static String stringify(Object object) {
        if (object == null) return "null";

        if (object instanceof Float) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }

        return object.toString();
    }
}
