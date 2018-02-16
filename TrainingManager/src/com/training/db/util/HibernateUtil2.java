package com.training.db.util;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.training.model.Abteilung;
import com.training.model.Anlage;
import com.training.model.Hersteller;
import com.training.model.Level;
import com.training.model.Mitarbeiter;
import com.training.model.Produkt;
import com.training.model.Schulung;
import com.training.model.Standort;
import com.training.model.Status;

public class HibernateUtil2 {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

				Map<String, String> settings = new HashMap<>();
				settings.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
				settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/trainingmanager");
				settings.put("hibernate.connection.username", "root");
				settings.put("hibernate.connection.password", "root");
				settings.put("hibernate.show_sql", "true");
				settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

				registryBuilder.applySettings(settings);

				registry = registryBuilder.build();

				MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Abteilung.class)
						.addAnnotatedClass(Anlage.class)
						.addAnnotatedClass(Hersteller.class).addAnnotatedClass(Level.class)
						.addAnnotatedClass(Mitarbeiter.class).addAnnotatedClass(Schulung.class)
						.addAnnotatedClass(Standort.class).addAnnotatedClass(Status.class)
						.addAnnotatedClass(Produkt.class);

				Metadata metadata = sources.getMetadataBuilder().build();

				sessionFactory = metadata.getSessionFactoryBuilder().build();
			} catch (Exception e) {
				System.out.println("SessionFactory creation failed");
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
