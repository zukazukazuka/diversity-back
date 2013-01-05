package com.github.zukazukazuka.diversity.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PluginResource {
	
	private static final String PLUGIN_NAME_PATTERN = "^([\\w\\-\\.]+?)(-[\\d\\.\\-]+?)(.*)$";

	private static final String VERSION_PATTERN = "^([\\w\\-\\.]+?)-(.*)$";

	private static final String SCRIPT_DIR_NAME ="/scripts";

	private static final String LIB_PREFIX = "_";

	public static final String UNSER_DEVELOP_VER ="UNDER-DEVELOP";
	
	public static final String SCRIPT_EXT = ".groovy";
	
	private File pluginDir ;
	
	private String pluginName;

	private String version;
	
	private Set<ScriptDescriptor> descriptors = new HashSet<ScriptDescriptor>();
	
	public PluginResource(File pluginDir) {
		this.pluginDir = pluginDir;
		String directoryName = pluginDir.getName();
		this.version = parseVersion(directoryName);
		this.pluginName = parsePluginName(directoryName , this.version);
		collectScripts();
	}

	protected void collectScripts() {
		File scriptDir = new File(this.pluginDir , SCRIPT_DIR_NAME);
		File[] scriptFiles = scriptDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return !name.startsWith(LIB_PREFIX) && name.endsWith(SCRIPT_EXT);
			}
		});
		for (File file : scriptFiles){
			this.descriptors.add(new ScriptDescriptor(this.pluginName, file));
		}
	}

	protected String parsePluginName(String directoryName , String version) {
		if (version.equals(UNSER_DEVELOP_VER)){
			return directoryName;
		}
		int lastIndex = directoryName.lastIndexOf(version);
		return directoryName.substring(0, lastIndex - 1);
	}

	protected String parseVersion(String directoryName) {
		Pattern pattern = Pattern.compile(PLUGIN_NAME_PATTERN);
		Matcher matcher = pattern.matcher(directoryName);
		if (!matcher.matches()) {
			return UNSER_DEVELOP_VER;
		}
		int index = matcher.start(2);
		return directoryName.substring(index + 1, directoryName.length());
	}

	public String getPluginName() {
		return this.pluginName;
	}

	public String getVersion() {
		return this.version;
	}

	public boolean isValid(){
		return !this.getScriptFiles().isEmpty();
	}
	
	public List<String> getScripts() {
		List<String> scripts = new LinkedList<String>();
		for (ScriptDescriptor desc :this.descriptors){
			scripts.add(desc.getScriptName());
		}
		return scripts;
	}

	public List<File> getScriptFiles() {
		List<File> scripts = new LinkedList<File>();
		for (ScriptDescriptor desc :this.descriptors){
			scripts.add(desc.getScriptFile());
		}
		return scripts;
	}

	public boolean isNewerVersion(PluginResource resource) {
		String version = this.getVersion();
		if (UNSER_DEVELOP_VER.equals(version)){
			return true;
		}
		String compareVersion = resource.getVersion();
		Pattern pattern = Pattern.compile(VERSION_PATTERN);
		Matcher matcherSelf = pattern.matcher(version);
		Matcher matcherCompare = pattern.matcher(compareVersion);
		if (matcherSelf.matches()){
			String selfMajorVer = matcherSelf.group(1);
			String selfRevision = matcherSelf.group(2);
			if (!matcherCompare.matches()){
				return selfMajorVer.compareTo(compareVersion) >= 0;
			}
			String compareMajorVer = matcherCompare.group(1);
			String compareRevision = matcherCompare.group(2);
			if (selfMajorVer.compareTo(compareMajorVer) == 0){
				return selfRevision.compareTo(compareRevision) >= 0;
			}
			return selfMajorVer.compareTo(compareMajorVer) >= 0;
		}else{
			if (matcherCompare.matches()){
				String compareMajorVer = matcherCompare.group(1);
				if (version.compareTo(compareMajorVer) == 0){
					return true;
				}
				return version.compareTo(compareMajorVer) >= 0;
			}
		}
		return version.compareTo(compareVersion) >= 0;
	}

	public Collection<ScriptDescriptor> getDescriptors(){
		return this.descriptors;
	}
	
}
