package io.github.nosequel.core.shared.logger;

public interface Logger {

    /**
     * Print a warning to the console
     *
     * @param string the string to print
     */
    void warning(String string);

    /**
     * Print info to the console
     *
     * @param string the string to print
     */
    void info(String string);

    /**
     * Print an error to the console
     *
     * @param string the string to print
     */
    void error(String string);

}
