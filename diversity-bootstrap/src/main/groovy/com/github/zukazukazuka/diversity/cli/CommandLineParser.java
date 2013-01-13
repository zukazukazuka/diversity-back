package com.github.zukazukazuka.diversity.cli;

import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.types.Commandline;

public class CommandLineParser {

	private Map<String, Option> declaredOptions = new HashMap<String, Option>();

	public void addOptions(String name, String description) {
		declaredOptions.put(name, new Option(name, description));
	}

	public CommandLine parseFromString(String input){
		String[] args = Commandline.translateCommandline(input);
		return this.parse(args);
	}
	
	public CommandLine parse(String... args) {
		DefaultCommandLine commandLine = new DefaultCommandLine();
		parse(commandLine, args);
		return commandLine;
	}

	protected void parse(DefaultCommandLine commandLine, String[] args) {
		boolean firstArgumentIsCommand = true;
		for (String arg : args) {
			if (arg == null)
				continue;
			String trimmed = arg.trim();
			if (trimmed != null && trimmed.length() > 0) {
				if (trimmed.charAt(0) == '-') {
					processOption(commandLine, trimmed);
				} else {
					if (firstArgumentIsCommand) {
						commandLine.setCommandName(trimmed);
						firstArgumentIsCommand = false;
					} else {
						commandLine.addRemainingArg(trimmed);
					}
				}
			}
		}
	}
	
    protected void processOption(DefaultCommandLine cl, String arg) {
        if (arg.length() < 2) {
            return;
        }

        if (arg.charAt(1) == 'D' && arg.contains("=")) {
            processSystemArg(cl, arg);
            return;
        }
        if (arg.charAt(1) == '-'){
        	arg = arg.substring(2, arg.length()).trim();
        }else{
        	arg = arg.substring(1, arg.length()).trim();
        }
        if (arg.contains("=")) {
            String[] split = arg.split("=");
            String name = split[0].trim();
            validateOptionName(name);
            String value = split[1].trim();
            if (declaredOptions.containsKey(name)) {
                cl.addDeclaredOption(name, declaredOptions.get(name), value);
            }
            else {
                cl.addUndeclaredOption(name, value);
            }
            return;
        }

        validateOptionName(arg);
        if (declaredOptions.containsKey(arg)) {
            cl.addDeclaredOption(arg, declaredOptions.get(arg));
        }
        else {
            cl.addUndeclaredOption(arg);
        }
    }
    
    private void validateOptionName(String name) {
        if (name.contains(" ")) throw new CommandLineParseException("Invalid argument: " + name);
    }

    protected void processSystemArg(DefaultCommandLine cl, String arg) {
        int i = arg.indexOf("=");
        String name = arg.substring(2, i);
        String value = arg.substring(i+1,arg.length());
        cl.addSystemProperty(name, value);
    }

}
