package com.home.gftest.singleton.simplecache;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.home.gftest.singleton.simplecache.model.ApplConfig;

@RunWith(Arquillian.class)
public class ApplConfigManagerTest {
	private static final Logger LOG = LogManager.getLogger(ApplConfigManagerTest.class);

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-persistence.xml"), "persistence.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"),
						"glassfish-ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(ApplConfigManager.class, ApplConfigService.class, ApplConfigManagerBean.class
						,ConfigCache.class, ConfigCacheBean.class
						,CacheDataProvider.class
						,CacheDataFromProperties.class);

		LOG.debug(archive.toString(true));

		return archive;
	}

	@EJB
	ApplConfigManager applConfigManager;

	@Test
	public void getAllTest()
	{
		LOG.debug("--> getAllTest");

		List<ApplConfig> configList = applConfigManager.getAll();
		assertTrue(configList.isEmpty());

		LOG.debug("<-- getAllTest");
	}
}
