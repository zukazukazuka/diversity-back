package com.github.zukazukazuka.diversity.console.command;

import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand;
import com.github.zukazukazuka.diversity.scripts.ScriptRunner;

public class ScriptCommand implements ConsoleHandleCommand {

	private ScriptRunner scriptRunner;

	public ScriptCommand(ScriptRunner scriptRunner){
		this.scriptRunner = scriptRunner;
	}
	
	@Override
	public STATUS execute(String input) {
		// TODO Auto-generated method stub
		return STATUS.CONTINE;
	}

}
