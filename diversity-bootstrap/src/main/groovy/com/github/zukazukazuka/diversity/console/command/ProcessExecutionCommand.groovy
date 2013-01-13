package com.github.zukazukazuka.diversity.console.command

import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand;
import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand.STATUS;
import com.github.zukazukazuka.diversity.console.InteractiveConsole;

class ProcessExecutionCommand implements ConsoleHandleCommand{

	static final String ARG_SPLIT_PATTERN = /(?<!\\)\s+/

	def InteractiveConsole console;
	
	def ProcessExecutionCommand(InteractiveConsole console){
		this.console = console;	
	}
	
	@Override
	public STATUS execute(String input) {
		if (!input.startsWith("!")){
			return STATUS.CONTINE
		}
		def args = input[1..-1].split(ARG_SPLIT_PATTERN).collect { unescape(it) }
		def process = new ProcessBuilder(args).redirectErrorStream(true).start()
		this.console.log process.inputStream.text
		return STATUS.DONE
	}

	protected unescape(String str) {
		return str.replace('\\', '')
	}
}
