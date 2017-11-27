package org.skyve.config;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "skyve")
@Validated
public class SkyveProperties {

	@Valid
	private final Trace trace = new Trace();
	@Valid
	private final Content content = new Content();
	@Valid
	private final Url url = new Url();
	@Valid
	private final Conversations conversations = new Conversations();
	@Valid
	private final Hibernate hibernate = new Hibernate();
	@Valid
	private final Factories factories = new Factories();
	@Valid
	private final Smtp smtp = new Smtp();
	@Valid
	private final Api api = new Api();
	@Valid
	private final Environment environment = new Environment();

	private List<ConfigDataStore> dataStores = new ArrayList<>();

	public Trace getTrace() {
		return trace;
	}

	public Content getContent() {
		return content;
	}

	public Url getUrl() {
		return url;
	}

	public Conversations getConversations() {
		return conversations;
	}

	public Hibernate getHibernate() {
		return hibernate;
	}

	public Factories getFactories() {
		return factories;
	}

	public Smtp getSmtp() {
		return smtp;
	}

	public Api getApi() {
		return api;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public List<ConfigDataStore> getDataStores() {
		return dataStores;
	}

	public void setDataStores(List<ConfigDataStore> dataStores) {
		this.dataStores = dataStores;
	}

	public static class Trace {
		@NotNull
		private Boolean xml;
		@NotNull
		private Boolean http;
		@NotNull
		private Boolean query;
		@NotNull
		private Boolean command;
		@NotNull
		private Boolean faces;
		@NotNull
		private Boolean sql;
		@NotNull
		private Boolean content;
		@NotNull
		private Boolean security;
		@NotNull
		private Boolean bizlet;
		@NotNull
		private Boolean dirty;

		public Boolean getXml() {
			return xml;
		}

		public void setXml(Boolean xml) {
			this.xml = xml;
		}

		public Boolean getHttp() {
			return http;
		}

		public void setHttp(Boolean http) {
			this.http = http;
		}

		public Boolean getQuery() {
			return query;
		}

		public void setQuery(Boolean query) {
			this.query = query;
		}

		public Boolean getCommand() {
			return command;
		}

		public void setCommand(Boolean command) {
			this.command = command;
		}

		public Boolean getFaces() {
			return faces;
		}

		public void setFaces(Boolean faces) {
			this.faces = faces;
		}

		public Boolean getSql() {
			return sql;
		}

		public void setSql(Boolean sql) {
			this.sql = sql;
		}

		public Boolean getContent() {
			return content;
		}

		public void setContent(Boolean content) {
			this.content = content;
		}

		public Boolean getSecurity() {
			return security;
		}

		public void setSecurity(Boolean security) {
			this.security = security;
		}

		public Boolean getBizlet() {
			return bizlet;
		}

		public void setBizlet(Boolean bizlet) {
			this.bizlet = bizlet;
		}

		public Boolean getDirty() {
			return dirty;
		}

		public void setDirty(Boolean dirty) {
			this.dirty = dirty;
		}
	}

	public static class Content {
		@NotEmpty
		private String directory;
		@NotEmpty
		private String gcCron;
		@NotNull
		private Boolean fileStorage;
		private String serverArgs;

		public String getDirectory() {
			return nullOrString(directory);
		}

		public void setDirectory(String directory) {
			this.directory = directory;
		}

		public String getGcCron() {
			return nullOrString(gcCron);
		}

		public void setGcCron(String gcCron) {
			this.gcCron = gcCron;
		}

		public Boolean getFileStorage() {
			return fileStorage;
		}

		public void setFileStorage(Boolean fileStorage) {
			this.fileStorage = fileStorage;
		}

		public String getServerArgs() {
			return serverArgs;
		}

		public void setServerArgs(String serverArgs) {
			this.serverArgs = serverArgs;
		}
	}

	public static class Url {
		@NotBlank
		private String server;
		@NotNull
		private String context;
		@NotBlank
		private String home;

		public String getServer() {
			return nullOrString(server);
		}

		public void setServer(String server) {
			this.server = server;
		}

		public String getContext() {
			return context;
		}

		public void setContext(String context) {
			this.context = context;
		}

		public String getHome() {
			return nullOrString(home);
		}

		public void setHome(String home) {
			this.home = home;
		}
	}

	public static class Conversations {
		@NotNull
		private Integer maxInMemory;
		@NotNull
		private Integer evictionTimeMinutes;

		public Integer getMaxInMemory() {
			return maxInMemory;
		}

		public void setMaxInMemory(Integer maxInMemory) {
			this.maxInMemory = maxInMemory;
		}

		public Integer getEvictionTimeMinutes() {
			return evictionTimeMinutes;
		}

		public void setEvictionTimeMinutes(Integer evictionTimeMinutes) {
			this.evictionTimeMinutes = evictionTimeMinutes;
		}
	}

	public static class ConfigDataStore {
		@NotBlank
		private String name;
		private String jndi;
		@NotBlank
		private String dialect;
		private String driver;
		private String url;
		private String user;
		private String password;

		public String getName() {
			return nullOrString(name);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getJndi() {
			return nullOrString(jndi);
		}

		public void setJndi(String jndi) {
			this.jndi = jndi;
		}

		public String getDialect() {
			return nullOrString(dialect);
		}

		public void setDialect(String dialect) {
			this.dialect = dialect;
		}

		public String getDriver() {
			return nullOrString(driver);
		}

		public void setDriver(String driver) {
			this.driver = driver;
		}

		public String getUrl() {
			return nullOrString(url);
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUser() {
			return nullOrString(user);
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return nullOrString(password);
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class Hibernate {
		@NotBlank
		private String dataStore;
		@NotNull
		private Boolean ddlSync;
		private String catalog;
		private String schema;
		private boolean prettySql;

		public String getDataStore() {
			return nullOrString(dataStore);
		}

		public void setDataStore(String dataStore) {
			this.dataStore = dataStore;
		}

		public Boolean getDdlSync() {
			return ddlSync;
		}

		public void setDdlSync(Boolean ddlSync) {
			this.ddlSync = ddlSync;
		}

		public String getCatalog() {
			return nullOrString(catalog);
		}

		public void setCatalog(String catalog) {
			this.catalog = catalog;
		}

		public String getSchema() {
			return nullOrString(schema);
		}

		public void setSchema(String schema) {
			this.schema = schema;
		}

		public boolean isPrettySql() {
			return prettySql;
		}

		public void setPrettySql(boolean prettySql) {
			this.prettySql = prettySql;
		}
	}

	public static class Factories {
		private String persistenceClass;
		private String repositoryClass;
		private String contentManagerClass;

		public String getPersistenceClass() {
			return nullOrString(persistenceClass);
		}

		public void setPersistenceClass(String persistenceClass) {
			this.persistenceClass = persistenceClass;
		}

		public String getRepositoryClass() {
			return nullOrString(repositoryClass);
		}

		public void setRepositoryClass(String repositoryClass) {
			this.repositoryClass = repositoryClass;
		}

		public String getContentManagerClass() {
			return nullOrString(contentManagerClass);
		}

		public void setContentManagerClass(String contentManagerClass) {
			this.contentManagerClass = contentManagerClass;
		}
	}

	public static class Smtp {
		@NotEmpty
		private String server;
		private int port;
		private String uid;
		private String pwd;
		private List<MailProperty> properties = new ArrayList<>();
		@NotEmpty
		private String sender;
		private boolean testBogusSend;
		private String testRecipient;

		public String getServer() {
			return nullOrString(server);
		}

		public void setServer(String server) {
			this.server = server;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getUid() {
			return nullOrString(uid);
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getPwd() {
			return nullOrString(pwd);
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

		public List<MailProperty> getProperties() {
			return properties;
		}

		public String getSender() {
			return nullOrString(sender);
		}

		public void setSender(String sender) {
			this.sender = sender;
		}

		public boolean isTestBogusSend() {
			return testBogusSend;
		}

		public void setTestBogusSend(boolean testBogusSend) {
			this.testBogusSend = testBogusSend;
		}

		public String getTestRecipient() {
			return nullOrString(testRecipient);
		}

		public void setTestRecipient(String testRecipient) {
			this.testRecipient = testRecipient;
		}
	}

	public static class MailProperty {
		private String name;
		private String value;

		public String getName() {
			return nullOrString(name);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return nullOrString(value);
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public static class Api {
		private String googleMapsV3Key;
		private String ckEditorConfigFileUrl;

		public String getGoogleMapsV3Key() {
			return nullOrString(googleMapsV3Key);
		}

		public void setGoogleMapsV3Key(String googleMapsV3Key) {
			this.googleMapsV3Key = googleMapsV3Key;
		}

		public String getCkEditorConfigFileUrl() {
			return nullOrString(ckEditorConfigFileUrl);
		}

		public void setCkEditorConfigFileUrl(String ckEditorConfigFileUrl) {
			this.ckEditorConfigFileUrl = ckEditorConfigFileUrl;
		}
	}

	public static class Environment {
		private String identifier;
		private boolean devMode;
		private String customer;
		private boolean jobScheduler;
		@NotEmpty
		private String passwordHashingAlgorithm;
		private String appsJarDirectory;

		public String getIdentifier() {
			return nullOrString(identifier);
		}

		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		public boolean isDevMode() {
			return devMode;
		}

		public void setDevMode(boolean devMode) {
			this.devMode = devMode;
		}

		public String getCustomer() {
			return nullOrString(customer);
		}

		public void setCustomer(String customer) {
			this.customer = customer;
		}

		public boolean isJobScheduler() {
			return jobScheduler;
		}

		public void setJobScheduler(boolean jobScheduler) {
			this.jobScheduler = jobScheduler;
		}

		public String getPasswordHashingAlgorithm() {
			return nullOrString(passwordHashingAlgorithm);
		}

		public void setPasswordHashingAlgorithm(String passwordHashingAlgorithm) {
			this.passwordHashingAlgorithm = passwordHashingAlgorithm;
		}

		public String getAppsJarDirectory() {
			return nullOrString(appsJarDirectory);
		}

		public void setAppsJarDirectory(String appsJarDirectory) {
			this.appsJarDirectory = appsJarDirectory;
		}
	}

	/**
	 * Returns null if the string is null or empty, or returns the string if contains a value.
	 * Required because Spring converts null YAML properties into empty string.
	 */
	private static String nullOrString(String string) {
		return StringUtils.isEmpty(string) ? null : string;
	}
}
