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
import com.training.model.Kategorie;

public class KategorieJDBCDAO implements KategorieDAO {

	private static final Logger logger = Logger.getLogger(KategorieJDBCDAO.class);

	private Session currentSession;

	@Override
	public void delete(Kategorie data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(data);

		tr.commit();

	}

	@Override
	public Kategorie get(int id) throws DAOException {
		Kategorie data = currentSession.get(Kategorie.class, id);
		return data;

	}

	@Override
	public List<Kategorie> getAll() throws DAOException {
		Transaction transaction = null;

		try {
			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Kategorie> query = builder.createQuery(Kategorie.class);

			Root<Kategorie> root = query.from(Kategorie.class);
			query.select(root);

			Query<Kategorie> q = currentSession.createQuery(query);
			List<Kategorie> data = q.getResultList();

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
	public void insert(Kategorie data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(data);

		tr.commit();

	}

	@Override
	public void update(Kategorie data) throws DAOException {

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
