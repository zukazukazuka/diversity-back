package com.github.zukazukazuka.diversity.cli;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface CommandLine {

    /**
     * @return The command name specified
     */
    String getCommandName();

    /**
     * @return The remaining arguments after the command name
     */
    List<String> getRemainingArgs();

    /**
     * @return The remaining arguments as an array
     */
    String[] getRemainingArgsArray();

    /**
     * @param name The name of the option
     * @return Whether the given option is specified
     */
    boolean hasOption(String name);

    /**
     * The value of an option
     * @param name The option
     * @return The value
     */
    Object optionValue(String name);

    /**
     * @return The remaining args as one big string
     */
    String getRemainingArgsString();

    /**
     * @return The remaining args separated by the line separator char
     */
    String getRemainingArgsLineSeparated();

    Map<String, Object> getUndeclaredOptions();

    void setCommand(String scriptName);

	public Properties getSystemProperties();

	public Map<String , Object> getAllOptions();
}
