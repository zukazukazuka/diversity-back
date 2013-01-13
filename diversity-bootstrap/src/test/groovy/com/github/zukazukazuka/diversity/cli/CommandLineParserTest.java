package com.github.zukazukazuka.diversity.cli;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class CommandLineParserTest {

	private CommandLineParser parser;

	@Before
	public void setUp() {
		this.parser = new CommandLineParser();
		this.parser.addOptions("debug", "debugingOption");
	}

	@Test
	public void testParseOption() {
		String[] args = new String[] { "-debug", "-verbose", "refresh" };
		CommandLine actual = this.parser.parse(args);
		assertThat(actual.hasOption("debug"), is(true));
	}

}
