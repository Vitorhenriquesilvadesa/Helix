package org.vtko.helix;

public class HelixException extends RuntimeException {

    HelixException(String message) {
        super("Helix Exception: " + message, null, false, false);
    }
}
