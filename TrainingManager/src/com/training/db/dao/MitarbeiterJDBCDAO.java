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
import com.training.model.Mitarbeiter;

public class MitarbeiterJDBCDAO implements MitarbeiterDAO {

	private static final Logger logger = Logger.getLogger(MitarbeiterJDBCDAO.class);

	private Session currentSession;

	@Override
	public void delete(Mitarbeiter mitarbeiter) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(mitarbeiter);

		tr.commit();

	}

	@Override
	public Mitarbeiter get(int id) throws DAOException {
		Mitarbeiter data = currentSession.get(Mitarbeiter.class, id);
		return data;

	}

	@Override
	public List<Mitarbeiter> getAll() throws DAOException {
		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Mitarbeiter> query = builder.createQuery(Mitarbeiter.class);

			Root<Mitarbeiter> root = query.from(Mitarbeiter.class);
			query.select(root);
			query.orderBy(builder.asc(root.get("nachname")), builder.asc(root.get("vorname")));

			Query<Mitarbeiter> q = currentSession.createQuery(query);
			List<Mitarbeiter> data = q.getResultList();

			transaction.commit();

			return data;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			currentSession.close();
		}
		return null;

	}

	@Override
	public void insert(Mitarbeiter mitarbeiter) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(mitarbeiter);

		tr.commit();

	}

	@Override
	public void update(Mitarbeiter mitarbeiter) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.update(mitarbeiter);

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
