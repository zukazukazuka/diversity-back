package com.github.zukazukazuka.diversity.console.command

import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand;
import com.github.zukazukazuka.diversity.console.ConsoleHandleCommand.STATUS;

class ProcessExecutionCommand implements ConsoleHandleCommand{

	static final String ARG_SPLIT_PATTERN = /(?<!\\)\s+/

	@Override
	public STATUS execute(String input) {
		if (!input.startsWith("!")){
			return STATUS.CONTINE;
		}
		def args = input[1..-1].split(ARG_SPLIT_PATTERN).collect { unescape(it) }
		def process = new ProcessBuilder(args).redirectErrorStream(true).start()
		// log process.inputStream.text
		return STATUS.CONTINE
	}

	protected unescape(String str) {
		return str.replace('\\', '')
	}
}
