package org.skyve.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.skyve.CORE;
import org.skyve.config.SkyveProperties.Api;
import org.skyve.config.SkyveProperties.ConfigDataStore;
import org.skyve.config.SkyveProperties.Content;
import org.skyve.config.SkyveProperties.Conversations;
import org.skyve.config.SkyveProperties.Environment;
import org.skyve.config.SkyveProperties.Factories;
import org.skyve.config.SkyveProperties.Hibernate;
import org.skyve.config.SkyveProperties.MailProperty;
import org.skyve.config.SkyveProperties.Smtp;
import org.skyve.config.SkyveProperties.Trace;
import org.skyve.config.SkyveProperties.Url;
import org.skyve.impl.content.AbstractContentManager;
import org.skyve.impl.content.elasticsearch.ESClient;
import org.skyve.impl.metadata.repository.AbstractRepository;
import org.skyve.impl.metadata.repository.LocalSecureRepository;
import org.skyve.impl.persistence.AbstractPersistence;
import org.skyve.impl.persistence.hibernate.HibernateContentPersistence;
import org.skyve.impl.util.UtilImpl;
import org.skyve.persistence.DataStore;
import org.skyve.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SkyveProperties.class)
public class SkyveConfiguration {

	private Logger logger = LoggerFactory.getLogger(SkyveConfiguration.class);

	private final SkyveProperties config;

	@Autowired
	public SkyveConfiguration(SkyveProperties config) {
		this.config = config;
	}

	@Value("${spring.mvc.view.prefix}")
	private String prefix;
	@Value("${spring.mvc.view.suffix}")
	private String suffix;
	@Value("${server.contextPath}")
	private String contextPath;

	@PostConstruct
	public void bootstrap() {
		// load skyve
		logo();

		loadApplicationProperties();
		
		logger.debug("Persistence implementaiton class: {}", AbstractPersistence.IMPLEMENTATION_CLASS);

		// initPersistence();

		/*final SuperUser user = new SuperUser();
		user.setCustomerName("bizhub");
		user.setName("TestUser");
		user.setId("TestUser");
		
		final AbstractPersistence persistence = AbstractPersistence.get();
		persistence.setUser(user);
		persistence.begin();*/

		// JobScheduler.init();
		// WebUtil.initConversationsCache();

		logger.debug("Server url: {}", config.getUrl().getServer());
		logger.debug("Context path: {}", contextPath);
	}

	public SkyveProperties getConfig() {
		return config;
	}

	/**
	 * Ensure that the schema is created before trying to init the job scheduler
	 */
	private void initPersistence() {
		Persistence p = null;
		try {
			p = CORE.getPersistence();
		} finally {
			if (p != null) {
				p.commit(true);
			}
		}
	}

	private void loadApplicationProperties() {
		UtilImpl.CONFIGURATION = new HashMap<String, Object>();

		parseTrace(config.getTrace());
		parseContent(config.getContent());
		parseUrl(config.getUrl());
		parseConversations(config.getConversations());
		parseDatastores(config.getDataStores());
		parseHibernate(config.getHibernate());
		parseFactories(config.getFactories());
		parseSmtp(config.getSmtp());
		parseApi(config.getApi());
		parseEnvironment(config.getEnvironment());
	}

	private void logo() {
		// System.out.println(" _____ __ ");
		// System.out.println(" / ___// /____ ___ _____ ");
		// System.out.println(" \\__ \\/ //_/ / / / | / / _ \\");
		// System.out.println(" ___/ / ,< / /_/ /| |/ / __/");
		// System.out.println("/____/_/|_|\\__, / |___/\\___/ ");
		// System.out.println(" /____/ ");

		System.out.println("            ...`           ");
		System.out.println("         :;;;;;;;:         ");
		System.out.println("       ,;;;;;;;;;;;,       ");
		System.out.println("      :;;;;;;;;;;;;;:      ");
		System.out.println("     :;;;;` `;;;;;;;;:     ");
		System.out.println("    ;;;;.,;;;,`;;;;;;;`    ");
		System.out.println("    ;;;,:;;;;;:.;;;;;;;    ");
		System.out.println("    ;;; ;;;;;;; ;;;;;;;       _____ __                  ");
		System.out.println("   :;;;`;;;;:,` `:;;;;;:     / ___// /____  ___   _____ ");
		System.out.println("   :;;;`:,        .;;;;;     \\__ \\/ //_/ / / / | / / _ \\");
		System.out.println("   ;;;;            ,;;;;    ___/ / ,< / /_/ /| |/ /  __/");
		System.out.println("   ;;;;:            ;;;;   /____/_/|_|\\__, / |___/\\___/ ");
		System.out.println("   :;;;;:       `::,;;;;             /____/             ");
		System.out.println("   ,;;;;;;:`,:;;;;;`;;;:   ");
		System.out.println("    ;;;;;;; ;;;;;;; ;;;    ");
		System.out.println("    ;;;;;;;:,;;;;;,:;;;    ");
		System.out.println("     ;;;;;;;,`:;;`,;;;     ");
		System.out.println("      ,;;;;;;;;;;;;;:      ");
		System.out.println("       `;;;;;;;;;;;`       ");
		System.out.println("         .;;;;;;;.         ");
		System.out.println("             `             ");
	}

	/**
	 * Parses the API section of the configuration
	 */
	private void parseApi(Api api) {
		UtilImpl.GOOGLE_MAPS_V3_API_KEY = api.getGoogleMapsV3Key();
		UtilImpl.CKEDITOR_CONFIG_FILE_URL = api.getCkEditorConfigFileUrl();
		if (UtilImpl.CKEDITOR_CONFIG_FILE_URL == null) {
			UtilImpl.CKEDITOR_CONFIG_FILE_URL = "";
		}
	}

	/**
	 * Parses the Content section of the configuration
	 */
	private void parseContent(Content content) {
		UtilImpl.CONTENT_DIRECTORY = content.getDirectory();
		File contentDirectory = new File(UtilImpl.CONTENT_DIRECTORY);
		if (!contentDirectory.exists()) {
			throw new IllegalStateException("content.directory " + UtilImpl.CONTENT_DIRECTORY + " does not exist.");
		}
		if (!contentDirectory.isDirectory()) {
			throw new IllegalStateException("content.directory " + UtilImpl.CONTENT_DIRECTORY + " is not a directory.");
		}
		// Check the content directory is writable
		File testFile = new File(contentDirectory, "SKYVE_TEST_WRITE_" + UUID.randomUUID().toString());
		try {
			testFile.createNewFile();
		} catch (Exception e) {
			throw new IllegalStateException("content.directory " + UtilImpl.CONTENT_DIRECTORY + " is not writeable.");
		} finally {
			testFile.delete();
		}
		UtilImpl.CONTENT_GC_CRON = content.getGcCron();
		UtilImpl.CONTENT_FILE_STORAGE = content.getFileStorage();
		UtilImpl.CONTENT_SERVER_ARGS = content.getServerArgs();
	}

	/**
	 * Parses the Conversations section of the configuration
	 */
	private void parseConversations(Conversations conversations) {
		UtilImpl.MAX_CONVERSATIONS_IN_MEMORY = conversations.getMaxInMemory();
		UtilImpl.CONVERSATION_EVICTION_TIME_MINUTES = conversations.getEvictionTimeMinutes();
	}

	/**
	 * Parses the list of Datastores of the configuration
	 */
	private void parseDatastores(List<ConfigDataStore> dataStores) {
		// for each datastore defined
		for (ConfigDataStore ds : config.getDataStores()) {
			String dialect = ds.getDialect();
			String jndi = ds.getJndi();

			if (jndi == null) {
				UtilImpl.DATA_STORES.put(ds.getName(),
						new DataStore(ds.getDriver(),
								ds.getUrl(),
								ds.getUser(),
								ds.getPassword(),
								dialect));
				logger.debug("Added datastore {} with url {}", ds.getName(), ds.getUrl());
			} else {
				UtilImpl.DATA_STORES.put(ds.getName(), new DataStore(jndi, dialect));
				logger.debug("Added datastore {} with jndi {}", ds.getName(), jndi);
			}
		}
	}

	/**
	 * Parses the Environment section of the configuration
	 */
	private void parseEnvironment(Environment environment) {
		UtilImpl.ENVIRONMENT_IDENTIFIER = environment.getIdentifier();
		UtilImpl.DEV_MODE = environment.isDevMode();
		UtilImpl.CUSTOMER = environment.getCustomer();
		UtilImpl.JOB_SCHEDULER = environment.isJobScheduler();
		UtilImpl.PASSWORD_HASHING_ALGORITHM = environment.getPasswordHashingAlgorithm();
		UtilImpl.APPS_JAR_DIRECTORY = environment.getAppsJarDirectory();
	}

	/**
	 * Parses the Factories section of the configuration
	 */
	@SuppressWarnings("unchecked")
	private void parseFactories(Factories factories) {
		// NB Need the repository set before setting persistence
		UtilImpl.SKYVE_REPOSITORY_CLASS = factories.getRepositoryClass();
		if (AbstractRepository.get() == null) {
			if (UtilImpl.SKYVE_REPOSITORY_CLASS == null) {
				UtilImpl.LOGGER.info("SET SKYVE REPOSITORY CLASS TO DEFAULT");
				AbstractRepository.set(new LocalSecureRepository());
			} else {
				UtilImpl.LOGGER.info("SET SKYVE REPOSITORY CLASS TO " + UtilImpl.SKYVE_REPOSITORY_CLASS);
				try {
					AbstractRepository.set((AbstractRepository) Thread.currentThread().getContextClassLoader()
							.loadClass(UtilImpl.SKYVE_REPOSITORY_CLASS).newInstance());
				} catch (Exception e) {
					throw new IllegalStateException("Could not create factories.repositoryClass " + UtilImpl.SKYVE_REPOSITORY_CLASS,
							e);
				}
			}
				}

		UtilImpl.SKYVE_PERSISTENCE_CLASS = factories.getPersistenceClass();
		if (AbstractPersistence.IMPLEMENTATION_CLASS == null) {
			if (UtilImpl.SKYVE_PERSISTENCE_CLASS == null) {
				UtilImpl.LOGGER.info("SET SKYVE PERSISTENCE CLASS TO DEFAULT");
				AbstractPersistence.IMPLEMENTATION_CLASS = HibernateContentPersistence.class;
			} else {
				UtilImpl.LOGGER.info("SET SKYVE PERSISTENCE CLASS TO " + UtilImpl.SKYVE_PERSISTENCE_CLASS);
				try {
					AbstractPersistence.IMPLEMENTATION_CLASS = (Class<? extends AbstractPersistence>) Class
							.forName(UtilImpl.SKYVE_PERSISTENCE_CLASS);
				} catch (ClassNotFoundException e) {
					throw new IllegalStateException("Could not find factories.persistenceClass " + UtilImpl.SKYVE_PERSISTENCE_CLASS,
							e);
				}
			}
				}

		UtilImpl.SKYVE_CONTENT_MANAGER_CLASS = factories.getContentManagerClass();
		if (UtilImpl.SKYVE_CONTENT_MANAGER_CLASS == null) {
			AbstractContentManager.IMPLEMENTATION_CLASS = ESClient.class;
		} else {
			try {
				AbstractContentManager.IMPLEMENTATION_CLASS = (Class<? extends AbstractContentManager>) Class
						.forName(UtilImpl.SKYVE_CONTENT_MANAGER_CLASS);
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException(
						"Could not find factories.contentManagerClass " + UtilImpl.SKYVE_CONTENT_MANAGER_CLASS, e);
			}
		}
	}

	/**
	 * Parses the Hibernate section of the configuration
	 */
	private void parseHibernate(Hibernate hibernate) {
		UtilImpl.DATA_STORE = UtilImpl.DATA_STORES.get(hibernate.getDataStore());
		if (UtilImpl.DATA_STORE == null) {
			throw new IllegalStateException("hibernate.dataStore " + UtilImpl.DATA_STORE + " is not defined in dataStores");
		}
		UtilImpl.DDL_SYNC = hibernate.getDdlSync();
		UtilImpl.CATALOG = hibernate.getCatalog();
		UtilImpl.SCHEMA = hibernate.getSchema();
		UtilImpl.PRETTY_SQL_OUTPUT = hibernate.isPrettySql();
	}

	/**
	 * Parses the SMTP section of the configuration
	 */
	private void parseSmtp(Smtp smtp) {
		UtilImpl.SMTP = smtp.getServer();
		UtilImpl.SMTP_PORT = String.valueOf(smtp.getPort());
		UtilImpl.SMTP_UID = smtp.getUid();
		UtilImpl.SMTP_PWD = smtp.getPwd();

		if (smtp.getProperties() != null && smtp.getProperties().size() > 0) {
			UtilImpl.SMTP_PROPERTIES = new TreeMap<>();
			for (MailProperty p : smtp.getProperties()) {
				if (p.getName() != null && p.getValue() != null) {
					UtilImpl.SMTP_PROPERTIES.put(p.getName(), p.getValue());
					logger.debug("Added mail property key: {} value: {}", p.getName(), p.getValue());
				}
			}
		}

		UtilImpl.SMTP_SENDER = smtp.getSender();
		UtilImpl.SMTP_TEST_RECIPIENT = smtp.getTestRecipient();
		UtilImpl.SMTP_TEST_BOGUS_SEND = smtp.isTestBogusSend();
		if (UtilImpl.SMTP_TEST_BOGUS_SEND && (UtilImpl.SMTP_TEST_RECIPIENT == null)) {
			throw new IllegalStateException("smtp.testBogusSend is true but no smtp.testRecipient is defined");
		}
	}

	/**
	 * Parses the Trace section of the configuration
	 */
	private void parseTrace(Trace trace) {
		UtilImpl.XML_TRACE = trace.getXml();
		UtilImpl.HTTP_TRACE = trace.getHttp();
		UtilImpl.COMMAND_TRACE = trace.getCommand();
		UtilImpl.FACES_TRACE = trace.getFaces();
		UtilImpl.QUERY_TRACE = trace.getQuery();
		UtilImpl.SQL_TRACE = trace.getSql();
		UtilImpl.CONTENT_TRACE = trace.getContent();
		UtilImpl.SECURITY_TRACE = trace.getSecurity();
		UtilImpl.BIZLET_TRACE = trace.getBizlet();
		UtilImpl.DIRTY_TRACE = trace.getDirty();
	}

	/**
	 * Parses the Url section of the configuration
	 */
	private void parseUrl(Url url) {
		// The following URLs cannot be set from the web context (could be many URLs to reach the web server after all).
		// There are container specific ways but we don't want that.
		UtilImpl.SERVER_URL = url.getServer();
		UtilImpl.SKYVE_CONTEXT = url.getContext();
		UtilImpl.HOME_URI = url.getHome();
	}
}
