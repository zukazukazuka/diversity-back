package com.github.zukazukazuka.diversity.console;

import java.io.IOException;
import java.util.Properties;

import com.github.zukazukazuka.diversity.configuration.BuildSettings;
import com.github.zukazukazuka.diversity.plugin.PluginRepository;
import com.github.zukazukazuka.diversity.scripts.ScriptRunner;

public class Bootstrap {
	
	public static void main(String... args) throws Exception{
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.run();
	}
	
	public Bootstrap() {
	}
	
	public void run() throws IOException{
		Properties props = System.getProperties();
		BuildSettings settings = new BuildSettings(props);
		settings.load();
		PluginRepository pluginRepository = new PluginRepository(settings);
		pluginRepository.load();
		ScriptRunner scriptRunner = this.createScriptRunner();
		
		InteractiveConsole console = InteractiveConsole.getInstance();
		ConsoleMediator mediator = new ConsoleMediator(scriptRunner , console ,pluginRepository);
		mediator.run();
	}

	protected ScriptRunner createScriptRunner() {
		return new ScriptRunner();
	}
}
