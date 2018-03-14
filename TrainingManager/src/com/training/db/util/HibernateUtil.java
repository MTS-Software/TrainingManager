package com.training.db.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.training.util.ApplicationProperties;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	private static SessionFactory buildSessionFactory() {

		try {
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure("hibernate.cfg.xml").build();

			Configuration cfg = new Configuration();
			cfg.configure();

			String url = "jdbc:mysql://" + ApplicationProperties.getInstance().getProperty("db_host") + ":"
					+ ApplicationProperties.getInstance().getProperty("db_port") + "/"
					+ ApplicationProperties.getInstance().getProperty("db_model");
			cfg.setProperty("hibernate.connection.url", url);
			cfg.setProperty("hibernate.connection.username",
					ApplicationProperties.getInstance().getProperty("db_user"));
			cfg.setProperty("hibernate.connection.password",
					ApplicationProperties.getInstance().getProperty("db_password"));
			sessionFactory = cfg.buildSessionFactory();

			// Metadata metaData = new
			// MetadataSources(standardRegistry).getMetadataBuilder().build();
			// sessionFactory = metaData.getSessionFactoryBuilder().build();

		} catch (Throwable th) {
			System.err.println("Enitial SessionFactory creation failed" + th);

			throw new ExceptionInInitializerError(th);
		}

		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		return sessionFactory;
	}

}
