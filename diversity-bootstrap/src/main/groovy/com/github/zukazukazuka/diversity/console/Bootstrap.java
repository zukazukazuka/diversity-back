package com.github.zukazukazuka.diversity.console;

import java.io.IOException;

import com.github.zukazukazuka.diversity.scripts.ScriptRunner;

public class Bootstrap {
	
	public static void main(String... args) throws Exception{
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.run();
	}
	
	public Bootstrap() {
	}
	
	public void run() throws IOException{
		ScriptRunner scriptRunner = this.createScriptRunner();
		InteractiveConsole console = InteractiveConsole.getInstance();
		ConsoleMediator mediator = new ConsoleMediator(scriptRunner , console);
		mediator.run();
	}

	protected ScriptRunner createScriptRunner() {
		return new ScriptRunner();
	}
}
