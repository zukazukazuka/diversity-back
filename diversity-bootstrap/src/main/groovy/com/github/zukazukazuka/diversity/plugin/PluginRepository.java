package com.github.zukazukazuka.diversity.plugin;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.zukazukazuka.diversity.configuration.BuildSettings;

public class PluginRepository {

	private final BuildSettings buildSettings;

	private Map<String , PluginResource> installed = new HashMap<String, PluginResource>();
	
	public PluginRepository(BuildSettings buildSettings){
		this.buildSettings = buildSettings;
	}
	
	public void load(){
		this.installed = new HashMap<String, PluginResource>();
		for (File pluginDirectory : this.buildSettings.getPluginDirectories()){
			File[] plugins = pluginDirectory.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isDirectory();
				}
			});
			if (plugins != null ){
				for (File eachPlugin : plugins){
					PluginResource resource = new PluginResource(eachPlugin);
					this.install(resource);
				}
			}
		}
	}

	public PluginResource getPlugin(String pluginName){
		return this.installed.get(pluginName);
	}
	
	public Collection<String> getPluginNames(){
		return this.installed.keySet();
	}
	
	public Collection<PluginResource> getAllPlugin(){
		return this.installed.values();
	}

	public int size(){
		return this.installed.size();
	}

	public Collection<ScriptDescriptor> getAllDescriptors(){
		List<ScriptDescriptor> descriptors = new ArrayList<ScriptDescriptor>();
		for (PluginResource plugin : this.getAllPlugin()){
			descriptors.addAll(plugin.getDescriptors());
		}
		return descriptors;
	}
	
	protected void install(PluginResource resource) {
		if (!resource.isValid()){
			return ;
		}
		PluginResource installedPlugin = this.installed.get(resource.getPluginName());
		if (installedPlugin != null){
			if (installedPlugin.isNewerVersion(resource)){
				return ;
			}
		}
		this.installed.put(resource.getPluginName(), resource);
	}
}
