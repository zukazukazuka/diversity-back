package com.github.zukazukazuka.diversity.console;

import java.util.HashSet;
import java.util.Set;

import jline.Completor;
import jline.SimpleCompletor;

import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand.STATUS;
import com.github.zukazukazuka.diversity.console.command.ProcessExecutionCommand;
import com.github.zukazukazuka.diversity.console.command.QuitCommand;
import com.github.zukazukazuka.diversity.console.command.ScriptCommand;
import com.github.zukazukazuka.diversity.scripts.ScriptRunner;

public class ConsoleMediator {

	private Set<ConsoleHandleCommand> commands = new HashSet<ConsoleHandleCommand>();

	private InteractiveConsole console;

	private ScriptRunner scriptRunner;

	public ConsoleMediator(ScriptRunner scriptRunner, InteractiveConsole console) {
		this.scriptRunner = scriptRunner;
		this.console = console;
		buildCommands();
		this.setUpCompletor();
	}

	protected void buildCommands() {
		this.commands.add(new QuitCommand());
		this.commands.add(new ScriptCommand(this.scriptRunner));
		this.commands.add(new ProcessExecutionCommand());
	}

	protected void setUpCompletor(){
		//TODO sample implementation
		Completor completor = new SimpleCompletor(new String[] { "foo", "bar",
        "baz" });
		this.console.addCompletor(completor);
	}
	
	public void run() {
		String line;
		while ((line = console.showPrompt()) != null) {
			STATUS status = null;
			for (ConsoleHandleCommand command : this.commands) {
				status = command.execute(line);
				if (status != null && STATUS.STOP == status) {
					break;
				}
			}
			if (status != null && STATUS.STOP == status) {
				break;
			} else {
				status = null;
			}
		}
	}
}
