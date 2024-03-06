package org.vtko.helix;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StdLibrary {
    private StdLibrary() {
    }

    static void defineNativeFunctions(Environment globals) {

        globals.define("loading", new HelixCallable.Native("loading") {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {

                int barSize = 360;
                float currentFilled = 0;
                int dotsLength = 1;
                int maxDaysOfYear = LocalDate.now().lengthOfYear();
                int currentYearDays = LocalDate.now().getDayOfYear();
                int currentPercent;
                StringBuilder builder = new StringBuilder();

                String[] progressCharacters = {" ", "▏", "▎", "▍", "▌", "▋", "▊", "▉", "█"};

                while (currentFilled <= currentYearDays) {
                    builder.setLength(0);
                    int filledBlocks = (int) Math.ceil(((barSize * currentFilled) / maxDaysOfYear));
                    int emptyBlocks = barSize - filledBlocks;

                    String filledChar = progressCharacters[progressCharacters.length - 1];

                    builder.append("\033[34m");
                    builder.append(filledChar.repeat(Math.max(0, filledBlocks / (progressCharacters.length))));

                    filledChar = progressCharacters[filledBlocks % (progressCharacters.length)];
                    builder.append(filledChar);

                    builder.append(" ".repeat(emptyBlocks / progressCharacters.length));
                    builder.append("\033[0m");
                    builder.append(": ");
                    builder.append(" ").append(currentYearDays).append(" / ").append(maxDaysOfYear);

                    currentPercent = (int) Math.ceil(((100f / maxDaysOfYear) * currentFilled));
                    System.out.print("\r" + "Current year loading" + ".".repeat(dotsLength) + " ".repeat(6 - dotsLength) + currentPercent + "%\t" + builder);
                    currentFilled += barSize / 365f;
                    dotsLength++;
                    if (dotsLength == 4) dotsLength = 1;

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                return null;
            }
        });

        globals.define("clock", new HelixCallable.Native("clock") {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return (float) System.currentTimeMillis();
            }
        });

        globals.define("exception", new HelixCallable.Native("exception") {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                System.err.println("\r" + arguments.getFirst().toString());
                System.exit(-1);

                return null;
            }
        });

        globals.define("print", new HelixCallable.Native("print") {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {

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
            public Object call(Interpreter interpreter, List<Object> arguments) {

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
            public Object call(Interpreter interpreter, List<Object> arguments) {

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
            public Object call(Interpreter interpreter, List<Object> arguments) {

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
            public Object call(Interpreter interpreter, List<Object> arguments) {

                int result;

                try {
                    result = (int) Float.parseFloat(arguments.getFirst().toString());
                } catch (NumberFormatException exception) {
                    throw new TypeCastException("Can't convert value to int.");
                }

                return result;
            }
        });

        globals.define("float", new HelixCallable.Native("float") {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {

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
