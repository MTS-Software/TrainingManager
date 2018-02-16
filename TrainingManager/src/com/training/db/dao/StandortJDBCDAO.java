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
import com.training.model.Standort;

public class StandortJDBCDAO implements StandortDAO {

	private static final Logger logger = Logger.getLogger(StandortJDBCDAO.class);

	private Session currentSession;

	@Override
	public void delete(Standort data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(data);

		tr.commit();

	}

	@Override
	public Standort get(int id) throws DAOException {
		Standort data = currentSession.get(Standort.class, id);
		return data;

	}

	@Override
	public List<Standort> getAll() throws DAOException {
		Transaction transaction = null;

		try {
			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Standort> query = builder.createQuery(Standort.class);

			Root<Standort> root = query.from(Standort.class);
			query.select(root);
			query.orderBy(builder.asc(root.get("name")));

			Query<Standort> q = currentSession.createQuery(query);
			List<Standort> data = q.getResultList();

			transaction.commit();

			return data;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return null;

	}

	@Override
	public void insert(Standort data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(data);

		tr.commit();

	}

	@Override
	public void update(Standort data) throws DAOException {

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
