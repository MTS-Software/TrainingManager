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
import com.training.model.Hersteller;

public class HerstellerJDBCDAO implements HerstellerDAO {

	private static final Logger logger = Logger.getLogger(HerstellerJDBCDAO.class);

	private Session currentSession;
	private Transaction currentTransaction;

	@Override
	public void delete(Hersteller data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(data);

		tr.commit();

	}

	@Override
	public Hersteller get(int id) throws DAOException {

		Hersteller data = currentSession.get(Hersteller.class, id);
		return data;

	}

	@Override
	public List<Hersteller> getAll() throws DAOException {
		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Hersteller> query = builder.createQuery(Hersteller.class);

			Root<Hersteller> root = query.from(Hersteller.class);
			query.select(root);
			query.orderBy(builder.asc(root.get("name")));

			Query<Hersteller> q = currentSession.createQuery(query);
			List<Hersteller> data = q.getResultList();

			transaction.commit();

			return data;

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
	public void insert(Hersteller data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(data);

		tr.commit();

	}

	@Override
	public void update(Hersteller data) throws DAOException {

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
