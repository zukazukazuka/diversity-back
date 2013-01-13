package com.github.zukazukazuka.diversity.scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gant.Gant;

import org.apache.tools.ant.BuildListener;
import org.codehaus.gant.GantBinding;

import com.github.zukazukazuka.diversity.plugin.ScriptDescriptor;

public class ScriptRunner {

	private List<BuildListener> listeners = new ArrayList<BuildListener>();
	
	public ScriptRunner(){
	}
	
	public void execute(ScriptDescriptor descriptor){
		File scriptFile = descriptor.getScriptFile();
		GantBinding binding = new GantBinding();
		Gant gant = new Gant(binding);
		for (BuildListener listener :this.listeners){
			gant.addBuildListener(listener);
		}
		gant.loadScript(scriptFile);
		gant.prepareTargets();
		gant.executeTargets();
	}
	
	public void addListener(BuildListener listener){
		this.listeners.add(listener);
	}
}
