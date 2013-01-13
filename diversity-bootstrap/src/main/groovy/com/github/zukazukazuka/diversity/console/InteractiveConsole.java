package com.github.zukazukazuka.diversity.console;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import jline.Completor;
import jline.ConsoleReader;

public class InteractiveConsole {

	private static InteractiveConsole INSTANCE = null;

	private static final String PROMPT = "console >";

	public static final Character SECURE_MASK_CHAR = new Character('*');

	protected ConsoleReader reader;

	private boolean userInputActive;

	private PrintStream out;

	private boolean ansiEnabled = true;

	public static InteractiveConsole getInstance() throws IOException {
		if (INSTANCE == null) {
			INSTANCE = new InteractiveConsole();
		}
		return INSTANCE;
	}

	protected InteractiveConsole() throws IOException {
		out = new PrintStream(System.out);
		this.reader = new ConsoleReader(System.in, new OutputStreamWriter(
				this.ansiWrap(out)));
		out.println();
	}

	public String showPrompt() {
		String prompt = isAnsiEnabled() ? ansiPrompt(PROMPT).toString()
				: PROMPT;
		return showPrompt(prompt);
	}

	public boolean isAnsiEnabled() {
		return Ansi.isEnabled()
		// && (terminal != null && terminal.isANSISupported())
				&& ansiEnabled;
	}

	public void addCompletor(Completor completor){
		this.reader.addCompletor(completor);
	}
	
	protected OutputStream ansiWrap(OutputStream out) {
		return AnsiConsole.wrapOutputStream(out);
	}

	private String showPrompt(String prompt) {
		if (!userInputActive) {
			return readLine(prompt, false);
		}
		out.print(prompt);
		return null;
	}

	private Ansi ansiPrompt(String prompt) {
		return ansi().a(Ansi.Attribute.INTENSITY_BOLD).fg(YELLOW).a(prompt)
				.a(Ansi.Attribute.INTENSITY_BOLD_OFF).fg(DEFAULT);
	}

	private String readLine(String prompt, boolean secure) {
		userInputActive = true;
		try {
			return secure ? reader.readLine(prompt, SECURE_MASK_CHAR) : reader
					.readLine(prompt);
		} catch (IOException e) {
			throw new RuntimeException("Error reading input: " + e.getMessage());
		} finally {
			userInputActive = false;
		}
	}

	public PrintStream getOut(){
		return this.out;
	}
}
