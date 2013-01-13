package com.github.zukazukazuka.diversity.console.command;

import java.util.Collection;

import com.github.zukazukazuka.diversity.cli.CommandLine;
import com.github.zukazukazuka.diversity.cli.CommandLineParser;
import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand;
import com.github.zukazukazuka.diversity.plugin.PluginRepository;
import com.github.zukazukazuka.diversity.plugin.ScriptDescriptor;
import com.github.zukazukazuka.diversity.scripts.ScriptRunner;

public class ScriptCommand implements ConsoleHandleCommand {

	private ScriptRunner scriptRunner;

	private PluginRepository pluginRepository;

	public ScriptCommand(ScriptRunner scriptRunner,
			PluginRepository pluginRepository) {
		this.scriptRunner = scriptRunner;
		this.pluginRepository = pluginRepository;
	}

	@Override
	public STATUS execute(String input) {
		CommandLine commandLine = createCommandLine(input);
		ScriptDescriptor executeScript = null;
		Collection<ScriptDescriptor> descriptors = this.pluginRepository
				.getAllDescriptors();
		for (ScriptDescriptor descriptor : descriptors) {
			if (descriptor.getDescription().equals(commandLine.getCommandName())) {
				executeScript = descriptor;
				break;
			}
		}
		if (executeScript == null) {
			return STATUS.CONTINE;
		}
		this.scriptRunner.execute(executeScript , commandLine);
		return STATUS.DONE;
	}

	private CommandLine createCommandLine(String input) {
		CommandLineParser parser = new CommandLineParser();
		return parser.parseFromString(input);
	}

}
