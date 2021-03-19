package com.home.gftest.singleton.simplecache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ConfigCacheTest {
	private static final Logger LOG = Logger.getLogger(ConfigCacheTest.class);

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"),
						"glassfish-ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(ConfigCache.class, ConfigCacheBean.class);

		System.out.println(archive.toString(true));

		return archive;
	}

	@EJB
	ConfigCache configCache;

	@Test
	public void getDataNoDefaultTest()
	{
		LOG.debug("--> getDataNoDefaultTest");

		String val = configCache.getData("kahdadhajkh");
		assertNull(val);

		val = configCache.getData("Key1");
		assertEquals(val, "Test 1");

		val = configCache.getData("Key2");
		assertEquals(val, "Test 2");

		val = configCache.getData("Key3");
		assertEquals(val, "Test 3");

		val = configCache.getData("Key4");
		assertTrue(val.isEmpty());

		LOG.debug("<-- getDataTest");
	}

	@Test
	public void getDataWithDefaultTest()
	{
		LOG.debug("--> getDataWithDefaultTest");

		String val = configCache.getData("kahdadhajkh", "ABC");
		assertEquals(val, "ABC");

		int numI = configCache.getData("Key5", 6);
		assertEquals(numI, 6);

		numI = configCache.getData("Key99", 2);
		assertEquals(numI, 2);

		long numL= configCache.getData("Key6", 77L);
		assertEquals(numL, 23);

		numL = configCache.getData("Key99", 11L);
		assertEquals(numL, 11L);

		LOG.debug("<-- getDataWithDefaultTest");
	}
}
