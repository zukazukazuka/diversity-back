package com.github.zukazukazuka.diversity.configuration;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static com.github.zukazukazuka.diversity.unit.CustomMatcher.*;


import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.github.zukazukazuka.diversity.configuration.BuildSettings;

public class BuildSettingsTest {

	private static final String ROOT_DIR= "./src/test/fixture/configuration";
	
	private BuildSettings buildSettings;
	
	private File baseDir = null;
	
	private File currentDir = null;
	
	private File userHome = null;
	
	@Before
	public void setUp() {
		this.currentDir = new File(ROOT_DIR + "/current");
		this.baseDir = new File(ROOT_DIR + "/diversity");
		this.userHome = new File(ROOT_DIR + "/userHome");
		Properties properties = System.getProperties();
		properties.setProperty("diversity.currentDir" , this.currentDir.getAbsolutePath());
		properties.setProperty("diversity.home", baseDir.getAbsolutePath());
		properties.setProperty("user.home", userHome.getAbsolutePath());
		this.buildSettings = new BuildSettings(properties);
	}
	
	@Test
	public void testGetUserHomeDirectory() {
		assertThat(buildSettings.getUserHomeDirectory(), is(this.userHome.getAbsolutePath()));
	}
	
	@Test
	public void testGetCurrentDirectory() {
		assertThat(buildSettings.getCurrentDirectory(), is(this.currentDir.getAbsolutePath()));
	}
	
	@Test
	public void testGetHomeDirectory() {
		assertThat(buildSettings.getHomeDirectory(), is(this.baseDir.getAbsolutePath()));
	}
	
	@Test
	public void testLoadTest() {
		this.buildSettings.load();
		assertThat(buildSettings.getString("diversity.sample1") , is("value1"));
		assertThat(buildSettings.getString("diversity.sample2") , is("value2"));
		assertThat(buildSettings.getString("diversity.sample3") , is("value3"));
		assertThat(buildSettings.getString("diversity.sample99") , is("valueAAA"));
	}
	
	@Test
	public void testGetPluginDirectories() {
		this.buildSettings.load();
		List<File> dirs = this.buildSettings.getPluginDirectories();
		assertThat(dirs.size(), is(3));
		Iterator<File> it = dirs.iterator();
		assertThat(it.next(), is(sameFile(ROOT_DIR + "/diversity/plugins")));
		assertThat(it.next(), is(sameFile(ROOT_DIR + "/userHome/diversity/plugins")));
		assertThat(it.next(), is(sameFile(ROOT_DIR + "/current/diversity/plugins")));
	}

	@Test
	public void testGetPluginDirectoriesWithGlobal() {
		this.buildSettings.setCustomSettings(new File(ROOT_DIR + "/settings.groovy"));
		this.buildSettings.load();
		List<File> dirs = this.buildSettings.getPluginDirectories();
		assertThat(dirs.size(), is(4));
		Iterator<File> it = dirs.iterator();
		assertThat(it.next(), is(sameFile(ROOT_DIR + "/diversity/plugins")));
		assertThat(it.next(), is(sameFile(ROOT_DIR + "/userHome/diversity/plugins")));
		assertThat(it.next(), is(sameFile(ROOT_DIR + "/custom/plugins")));
		assertThat(it.next(), is(sameFile(ROOT_DIR + "/current/diversity/plugins")));
	}

}
