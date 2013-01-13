package com.github.zukazukazuka.diversity.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jline.Completor;
import jline.SimpleCompletor;

import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand.STATUS;
import com.github.zukazukazuka.diversity.console.command.PluginCommand;
import com.github.zukazukazuka.diversity.console.command.ProcessExecutionCommand;
import com.github.zukazukazuka.diversity.console.command.QuitCommand;
import com.github.zukazukazuka.diversity.console.command.ScriptCommand;
import com.github.zukazukazuka.diversity.plugin.PluginRepository;
import com.github.zukazukazuka.diversity.plugin.ScriptDescriptor;
import com.github.zukazukazuka.diversity.scripts.ScriptRunner;

public class ConsoleMediator {

	private Set<ConsoleHandleCommand> commands = new HashSet<ConsoleHandleCommand>();

	private InteractiveConsole console;

	private ScriptRunner scriptRunner;

	private PluginRepository pluginRepository;

	public ConsoleMediator(ScriptRunner scriptRunner, InteractiveConsole console ,PluginRepository pluginRepository ) {
		this.scriptRunner = scriptRunner;
		this.console = console;
		this.pluginRepository = pluginRepository;
		buildCommands();
		this.setUpCompletor();
		//this.scriptRunner.addListener(new ConsoleBuildLogger(console));
	}

	protected void buildCommands() {
		this.commands.add(new QuitCommand());
		this.commands.add(new ScriptCommand(this.scriptRunner , pluginRepository));
		this.commands.add(new ProcessExecutionCommand(this.console));
		this.commands.add(new PluginCommand(this.pluginRepository));
	}

	protected void setUpCompletor(){
		Collection<ScriptDescriptor> descriptors = this.pluginRepository.getAllDescriptors();
		List<String> candicates = new ArrayList<String>();
		for(ScriptDescriptor descriptor:descriptors){
			candicates.add(descriptor.getDescription());
		}
		Completor completor = new SimpleCompletor(candicates.toArray(new String[0]));
		this.console.addCompletor(completor);
	}
	
	public void run() {
		String line;
		while ((line = console.showPrompt()) != null) {
			STATUS status = null;
			for (ConsoleHandleCommand command : this.commands) {
				status = command.execute(line);
				if (status != null && (STATUS.STOP == status || STATUS.DONE == status)) {
					break;
				}
			}
			if (status != null && STATUS.STOP == status) {
				break;
			} else {
				status = null;
			}
		}
	}
}
