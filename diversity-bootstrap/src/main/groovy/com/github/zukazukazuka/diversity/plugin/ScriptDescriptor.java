package com.github.zukazukazuka.diversity.plugin;

import java.io.File;

public class ScriptDescriptor {

	private final String pluginName;
	
	private final File scriptFile;

	private String scriptName;
	
	public ScriptDescriptor(String pluginName , File scriptFile){
		this.pluginName = pluginName;
		this.scriptFile = scriptFile;
		String fileName = scriptFile.getName();
		int lastIndex = fileName.lastIndexOf(PluginResource.SCRIPT_EXT);
		this.scriptName = fileName.substring(0 , lastIndex);
	}

	public String getPluginName() {
		return pluginName;
	}

	public File getScriptFile() {
		return scriptFile;
	}

	public String getScriptName(){
		return this.scriptName;
	}
	
	public String getDescription(){
		return this.pluginName + ":" + this.scriptName;
	}
}
