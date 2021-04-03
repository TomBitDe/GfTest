package com.home.gftest.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jline.utils.Log;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.home.gftest.jpa.model.Address;
import com.home.gftest.jpa.model.User;

/**
 * Test the user address manager session bean.
 */
@RunWith(Arquillian.class)
public class UserAddressTest {
	private static final Logger LOG = LogManager.getLogger(UserAddressTest.class);

	@EJB
	private UserAddressManagerLocal userAddressManager;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-*.xml in JARs META-INF folder as *.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-persistence.xml"), "persistence.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"), "glassfish-ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						UserAddressManagerLocal.class, UserAddressManagerBean.class
						);

		LOG.debug(archive.toString(true));

		return archive;
	}

	/**
	 * Simple user with the mandatory reference to address
	 */
	@Test
	@InSequence(0)
	public void create() {
		LOG.info("Test create()");

		Address expAddress = new Address("4711");

		User expUser = new User("Tom", expAddress); // User needs the address

		expAddress.setUser(expUser); // Set the bidirectional attribute in address

		userAddressManager.create(expUser); // Persist the user only; address is persisted because of CASCADE

		User user = userAddressManager.getById(expUser.getId());
		assertEquals(expUser, user);

		Address address = userAddressManager.getByUserId(expUser.getId());

		// Address user has already been set
		assertNotNull(address.getUser());

		LOG.info(user);
	}

	@Test(expected=EJBException.class)
	@InSequence(1)
	public void invalidCreate() {
		LOG.info("Test invalidCreate()");

		User expUser = new User();
		expUser.setName("Hugo");

		// No address specified, invalid
		userAddressManager.create(expUser);
	}

	@Test
	@InSequence(2)
	public void setUserInAddress() {
		LOG.info("Test setUserInAddress");

		User user = userAddressManager.getById(1L);
		assertNotNull(user);

		userAddressManager.setUserInAddress(user);

		Address address = userAddressManager.getByUserId(1L);
		assertNotNull(address);
		assertNotNull(address.getUser());

		LOG.info(address);
	}

	@Test
	@InSequence(3)
	public void complete() {
		LOG.info("Test complete()");

		User expUser = userAddressManager.getById(1L);
		assertNotNull(expUser);

		LOG.info(expUser);

		assertNotNull(expUser.getId());
		assertNotNull(expUser.getName());
		assertNotNull(expUser.getVersion());
		assertNotNull(expUser.getAddress());
		assertNotNull(expUser.getAddress().getId());
		assertNotNull(expUser.getAddress().getVersion());
		assertNotNull(expUser.getAddress().getUser());
		assertEquals(expUser.getAddress().getUser().getId(), expUser.getId());
	}

	@Test
	@InSequence(4)
	public void getAll() {
		LOG.info("Test getAll()");

		List<User> users = userAddressManager.getAll();
		assertNotNull(users);
		assertFalse(users.isEmpty());

		users.forEach(elem -> { Log.info(elem); });
	}

	@Test
	@InSequence(5)
	public void getByUserId() {
		LOG.info("getByUserId()");

		Address expResult = userAddressManager.getByUserId(1L);

		Address result = userAddressManager.getById(1L).getAddress();

		assertEquals(expResult, result);
	}

	@Test
	@InSequence(6)
	public void getByUser() {
		LOG.info("getByUser()");

		Address expResult = userAddressManager.getByUserId(1L);

		User user = userAddressManager.getById(1L);
		assertNotNull(user);

		Address result = userAddressManager.getByUser(user);

		assertEquals(expResult, result);
	}

	@Test
	@InSequence(98)
	public void deleteById() {
		LOG.info("deleteById()");

		User expResult = userAddressManager.getById(1L);
		assertNotNull(expResult);

		User result = userAddressManager.delete(1L);
		assertEquals(expResult, result);

		result = userAddressManager.getById(1L);
		assertNull(result);

	}

	@Test
	@InSequence(99)
	public void deleteByUser() {
		LOG.info("deleteByUser()");

		User expUser = new User("Emil", new Address("4712"));

		userAddressManager.create(expUser);

		User user = userAddressManager.getById(expUser.getId());
		assertEquals(expUser, user);

		User expResult = userAddressManager.getById(user.getId());
		assertNotNull(expResult);

		User result = userAddressManager.delete(expResult);
		assertEquals(expResult, result);
	}
}