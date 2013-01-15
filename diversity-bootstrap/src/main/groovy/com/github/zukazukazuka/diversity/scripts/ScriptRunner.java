package com.github.zukazukazuka.diversity.scripts;

import gant.Gant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildListener;
import org.codehaus.gant.GantBinding;

import com.github.zukazukazuka.diversity.cli.CommandLine;
import com.github.zukazukazuka.diversity.plugin.ScriptDescriptor;
	
public class ScriptRunner {

	private List<BuildListener> listeners = new ArrayList<BuildListener>();
	
	public ScriptRunner(){
	}
	
	public void execute(ScriptDescriptor descriptor ,CommandLine commandLine){
		File scriptFile = descriptor.getScriptFile();
		GantBinding binding = new GantBinding();
		binding.setProperty("args", commandLine.getRemainingArgs());
		binding.setVariable("options", commandLine.getAllOptions());
		Gant gant = new Gant(binding);
		for (BuildListener listener :this.listeners){
			gant.addBuildListener(listener);
		}
		gant.loadScript(scriptFile);
		gant.prepareTargets();
		if (commandLine.hasOption("help")){
			List<String> targetNames = new ArrayList<String>();
			targetNames.add("help");
			gant.executeTargets("dispatch" , targetNames);
			return ;
		}
		gant.executeTargets();
	}
	
	public void addListener(BuildListener listener){
		this.listeners.add(listener);
	}
}
