package com.github.zukazukazuka.diversity.console.command;

import java.util.Collection;

import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand;
import com.github.zukazukazuka.diversity.plugin.PluginRepository;
import com.github.zukazukazuka.diversity.plugin.ScriptDescriptor;
import com.github.zukazukazuka.diversity.scripts.ScriptRunner;

public class ScriptCommand implements ConsoleHandleCommand {

	private ScriptRunner scriptRunner;
	
	private PluginRepository pluginRepository;


	public ScriptCommand(ScriptRunner scriptRunner, PluginRepository pluginRepository ){
		this.scriptRunner = scriptRunner;
		this.pluginRepository = pluginRepository;
	}
	
	@Override
	public STATUS execute(String input) {
		ScriptDescriptor executeScript = null;
		Collection<ScriptDescriptor> descriptors = this.pluginRepository.getAllDescriptors();
		for (ScriptDescriptor descriptor : descriptors){
			if (descriptor.getDescription().equals(input)){
				executeScript = descriptor;
				break;
			}
		}
		if (executeScript == null){
			System.out.println("Script Not Found");
			return STATUS.CONTINE;
		}else{
			this.scriptRunner.execute(executeScript);
		}
		return STATUS.CONTINE;
	}

}
