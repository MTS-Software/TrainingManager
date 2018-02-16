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
import com.training.model.Level;

public class LevelJDBCDAO implements LevelDAO {

	private static final Logger logger = Logger.getLogger(LevelJDBCDAO.class);
	private Session currentSession;

	@Override
	public void delete(Level level) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(level);

		tr.commit();

	}

	@Override
	public Level get(int id) throws DAOException {
		Level data = currentSession.get(Level.class, id);
		return data;

	}

	@Override
	public List<Level> getAll() throws DAOException {
		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Level> query = builder.createQuery(Level.class);

			Root<Level> root = query.from(Level.class);
			query.select(root);
			 query.orderBy(builder.asc(root.get("level")));

			Query<Level> q = currentSession.createQuery(query);
			List<Level> levelen = q.getResultList();

			transaction.commit();

			return levelen;

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
	public void insert(Level level) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(level);

		tr.commit();

	}

	@Override
	public void update(Level level) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.update(level);

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
