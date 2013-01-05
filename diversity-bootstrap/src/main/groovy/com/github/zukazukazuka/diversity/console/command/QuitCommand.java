package com.github.zukazukazuka.diversity.console.command;

import java.util.HashSet;
import java.util.Set;

import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand;

public class QuitCommand implements ConsoleHandleCommand {

	private Set<String> stopKeywords = new HashSet<String>();
	
	public QuitCommand(){
		stopKeywords.add("bye");
		stopKeywords.add("quit");
		stopKeywords.add("exit");
	}

	@Override
	public STATUS execute(String input) {
		if (stopKeywords.contains(input)){
			return STATUS.STOP;
		}
		return STATUS.CONTINE;
	}
}
