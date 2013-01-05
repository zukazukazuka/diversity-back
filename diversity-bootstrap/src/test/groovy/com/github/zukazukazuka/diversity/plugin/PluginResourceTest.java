package com.github.zukazukazuka.diversity.plugin;

import static org.hamcrest.CoreMatchers.*;
import static com.github.zukazukazuka.diversity.unit.CustomMatcher.*;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.zukazukazuka.diversity.plugin.PluginResource;

public class PluginResourceTest {
	
	private File pluginDir;
	
	private File emptyPluginDir;
	
	private String pluginBaseDir = "./src/test/fixture/plugins/plugins/";
	
	@Before
	public void setUp() {
		String pathname = pluginBaseDir + "s2jdbc-gen-0.0.1";
		this.pluginDir = new File(pathname);
		String emptyPluginDirName = pluginBaseDir + "empty-plugin-0.0.0.2";
		this.emptyPluginDir = new File(emptyPluginDirName);
	}
					
	@Test
	public void testConstructor() {
		PluginResource resource = new PluginResource(pluginDir);
		assertThat(resource.getPluginName(), is("s2jdbc-gen"));
		assertThat(resource.getVersion(), is("0.0.1"));
	}
	
	@Test
	public void testConstructorUnserDevelopment() {
		File plugin = new File(this.pluginBaseDir + "under-develop");
		PluginResource resource = new PluginResource(plugin);
		assertThat(resource.getPluginName(), is("under-develop"));
		assertThat(resource.getVersion(), is(PluginResource.UNSER_DEVELOP_VER));
		assertThat(resource.isValid(), is(true));
	}
	
	@Test
	public void testParsePluginName() {
		PluginResource resource = new PluginResource(pluginDir);
		assertThat(resource.parsePluginName("s2jdbc-gen-0.0.1" , "0.0.1"), is("s2jdbc-gen"));
		assertThat(resource.parsePluginName("s2jdbc-gen-0.0.1-SNAPSHOT" ,"0.0.1-SNAPSHOT"), is("s2jdbc-gen"));
		assertThat(resource.parsePluginName("s2jdbc-gen-0.0.1-RC1" ,"0.0.1-RC1"), is("s2jdbc-gen"));
	}
	@Test
	public void testParseVersion() {
		PluginResource resource = new PluginResource(pluginDir);
		assertThat(resource.parseVersion("s2jdbc-gen-0.0.1"), is("0.0.1"));
		assertThat(resource.parseVersion("s2jdbc-gen-0.0.1-SNAPSHOT"), is("0.0.1-SNAPSHOT"));
		assertThat(resource.parseVersion("s2jdbc-gen-0.0.1-RC1"), is("0.0.1-RC1"));
	}

	@Test
	public void testGetScriptFiles() {
		PluginResource resource = new PluginResource(pluginDir);
		List<File> scripts = resource.getScriptFiles();
		assertThat(scripts.size(), is(2));
		assertThat(scripts, containsFileName("gen-entities.groovy"));
		assertThat(scripts, containsFileName("gen-names.groovy"));
	}
	
	@Test
	public void testIsValid() {
		assertThat(new PluginResource(pluginDir).isValid(), is(true));
		assertThat(new PluginResource(emptyPluginDir).isValid(), is(false));
	}
	
	@Test
	public void testIsNewerVersion(){
		PluginResource resource1 = spy(new PluginResource(pluginDir));
		when(resource1.getVersion()).thenReturn("0.0.1");
		PluginResource resource2 = spy(new PluginResource(pluginDir));
		when(resource2.getVersion()).thenReturn("0.0.2");
		PluginResource resource3 = spy(new PluginResource(pluginDir));
		when(resource3.getVersion()).thenReturn("0.0.2-SNAPSHOT");
		
		PluginResource resource4 = spy(new PluginResource(pluginDir));
		when(resource4.getVersion()).thenReturn(PluginResource.UNSER_DEVELOP_VER);
		
		assertThat(resource1.isNewerVersion(resource1) ,is(true));
		assertThat(resource2.isNewerVersion(resource1) ,is(true));
		assertThat(resource2.isNewerVersion(resource3) ,is(true));
		assertThat(resource4.isNewerVersion(resource1) , is(true));
	}
}
