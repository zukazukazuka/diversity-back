package com.github.zukazukazuka.diversity.console;

public interface ConsoleHandleCommand {

	enum STATUS {
		DONE,
		CONTINE,
		STOP;
	}
	
	public STATUS execute(String input);
}
