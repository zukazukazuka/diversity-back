package com.github.zukazukazuka.diversity.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class DefaultCommandLine implements CommandLine {

	private Map<String, Object> undeclaredOptions = new HashMap<String, Object>();
	private Map<String, SpecifiedOption> declaredOptions = new HashMap<String, SpecifiedOption>();
	private List<String> remainingArgs = new ArrayList<String>();
	private String commandName;
	
	private Properties systemProperties = new Properties();
	
	public static class SpecifiedOption{
		private Option option;
		private Object value;

		public Option getOption() {
			return option;
		}

		public Object getValue() {
			return value;
		}
	}

	public void addSystemProperty(String key , String value){
		this.systemProperties.put(key, value);
	}
	
	@Override
	public Properties getSystemProperties(){
		return this.systemProperties;
	}
	
	public void addDeclaredOption(String name, Option option) {
		addDeclaredOption(name, option, Boolean.TRUE);
	}

	public void addUndeclaredOption(String option) {
		undeclaredOptions.put(option, Boolean.TRUE);
	}

	public void addUndeclaredOption(String option, Object value) {
		undeclaredOptions.put(option, value);
	}

	public void addDeclaredOption(String name, Option option, Object value) {
		SpecifiedOption so = new SpecifiedOption();
		so.option = option;
		so.value = value;
		declaredOptions.put(name, so);
	}

	@Override
	public void setCommand(String name) {
		commandName = name;
	}

	public void setCommandName(String cmd) {
		this.commandName = cmd;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	public void addRemainingArg(String arg) {
		remainingArgs.add(arg);
	}

	@Override
	public List<String> getRemainingArgs() {
		return remainingArgs;
	}

	@Override
	public String[] getRemainingArgsArray() {
		return remainingArgs.toArray(new String[remainingArgs.size()]);
	}

	@Override
	public boolean hasOption(String name) {
		return declaredOptions.containsKey(name)
				|| undeclaredOptions.containsKey(name);
	}

	@Override
	public Object optionValue(String name) {
		if (declaredOptions.containsKey(name)) {
			SpecifiedOption specifiedOption = declaredOptions.get(name);
			return specifiedOption.value;
		}
		if (undeclaredOptions.containsKey(name)) {
			return undeclaredOptions.get(name);
		}
		return null;
	}

	@Override
	public String getRemainingArgsString() {
		return remainingArgsToString(" ");
	}

	@Override
	public String getRemainingArgsLineSeparated() {
		return remainingArgsToString("\n");
	}

	private String remainingArgsToString(String separator) {
		StringBuilder sb = new StringBuilder();
		String sep = "";
		List<String> args = new ArrayList<String>(remainingArgs);
		for (Map.Entry<String, Object> entry : undeclaredOptions.entrySet()) {
			if (entry.getValue() instanceof Boolean
					&& ((Boolean) entry.getValue())) {
				args.add('-' + entry.getKey());
			} else {
				args.add('-' + entry.getKey() + '=' + entry.getValue());
			}
		}
		for (String arg : args) {
			sb.append(sep).append(arg);
			sep = separator;
		}
		return sb.toString();
	}

	@Override
	public Map<String, Object> getUndeclaredOptions() {
		return undeclaredOptions;
	}

	@Override
	public Map<String, Object> getAllOptions() {
		Map<String , Object> options = new HashMap<String, Object>();
		for (Entry<String, SpecifiedOption> option :this.declaredOptions.entrySet()){
			options.put(option.getKey(), option.getValue().getValue());
		}
		for (Entry<String, Object> option : this.undeclaredOptions.entrySet()){
			options.put(option.getKey(), option.getValue());
		}
		return options;
	}
	
	
}
