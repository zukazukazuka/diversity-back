package com.github.zukazukazuka.diversity.configuration

class BuildSettings {

	static final String CONFIG_FILE_NAME = "settings.groovy"
	
	def ConfigObject config;
	
	def File customSettings = null; 
	
	protected List<File> pluginDirectories = [];
	
	def BuildSettings(Properties props){
		this.config = new ConfigSlurper().parse(props)
	}
	
	def String getUserHomeDirectory(){
		this.config.user.home;
	}
	
	def String getCurrentDirectory(){
		return config.diversity.currentDir;
	}
	
	def String getHomeDirectory() {
		return config.diversity.home;
	}
	
	def List<File> getPluginDirectories(){
		return this.pluginDirectories;
	}
	
	def void addPluginDirectory(File location){
		this.pluginDirectories.add(new File(location.canonicalPath));
	}
	
	def getGlobalPluginDirectory(){
		return this.config.diversity.plugin.global;
	}
	
	def load(){
		def configs = [
			this.getHomeDirectory() + "/config/${CONFIG_FILE_NAME}" ,
			this.getUserHomeDirectory() + "/diversity/${CONFIG_FILE_NAME}",
			this.getCurrentDirectory() + "/diversity/${CONFIG_FILE_NAME}"
			];
		if (this.customSettings){
			configs.push(this.customSettings.canonicalPath);	
		}
		configs.each {
			this.loadAndMerge(it);
		}
		configurePluginDirectories();
	}
	
	def Object getValue(String key){
		def String lastToken = key;
		def ConfigObject toSearch = this.config;
		if (key.indexOf(".") > 0){
			String[] token = key.split("\\.");
			lastToken = token[token.length -1];
			for (int i = 0 ; i < token.length -1 ; i ++){
				toSearch = ((ConfigObject)toSearch).get(token[i]);
			}
		}
		return toSearch.get(lastToken);
	}
	def String getString(String key){
		return (String)this.getValue(key);
	}
	
	protected def configurePluginDirectories() {
		def dirs = [
			this.getHomeDirectory() + "/plugins" ,
			this.getUserHomeDirectory() + "/diversity/plugins"
			];
		def globalPluginDir = this.getGlobalPluginDirectory();
		if (globalPluginDir){
			dirs.push(globalPluginDir)
		}
		dirs.push(this.getCurrentDirectory() + "/diversity/plugins")
		dirs.each {
			File dir = new File(it);
			if (dir.exists() && dir.isDirectory()){
				this.addPluginDirectory(dir);
			}
		}	
	}
	
	protected def loadAndMerge(String path){
		File configFile = new File(path.trim());
		if (configFile.exists()){
			def localConfig = new ConfigSlurper().parse(configFile.toURI().toURL());
			this.config = this.config.merge(localConfig);
		}
	}
}
