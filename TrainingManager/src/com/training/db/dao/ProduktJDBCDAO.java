package com.training.db.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.training.db.util.DAOException;
import com.training.db.util.HibernateUtil;
import com.training.model.Produkt;

public class ProduktJDBCDAO implements ProduktDAO {

	private static final Logger logger = Logger.getLogger(ProduktJDBCDAO.class);

	private Session currentSession;
	private Transaction currentTransaction;

	@Override
	public void delete(Produkt data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(data);

		tr.commit();

	}

	@Override
	public Produkt get(int id) throws DAOException {
		Produkt data = currentSession.get(Produkt.class, id);
		return data;

	}

	@Override
	public List<Produkt> getAll() throws DAOException {

		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Produkt> query = builder.createQuery(Produkt.class);

			Root<Produkt> root = query.from(Produkt.class);
			query.select(root);
			query.orderBy(builder.asc(root.get("name")));

			Query<Produkt> q = currentSession.createQuery(query);
			List<Produkt> dataen = q.getResultList();

			transaction.commit();

			return dataen;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {

		}
		return null;

	}

	@Override
	public void insert(Produkt data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(data);

		tr.commit();

	}

	@Override
	public void update(Produkt data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.update(data);

		tr.commit();

	}

	public Session openCurrentSession() {

		currentSession = HibernateUtil.getSessionFactory().openSession();

		return currentSession;

	}

	public void closeCurrentSession() {

		currentSession.close();

	}

}
