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
import com.training.model.Status;

public class StatusJDBCDAO implements StatusDAO {

	private static final Logger logger = Logger.getLogger(StatusJDBCDAO.class);

	private Session currentSession;

	@Override
	public void delete(Status data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(data);

		tr.commit();

	}

	@Override
	public Status get(int id) throws DAOException {
		Status data = currentSession.get(Status.class, id);
		return data;

	}

	@Override
	public List<Status> getAll() throws DAOException {
		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Status> query = builder.createQuery(Status.class);

			Root<Status> root = query.from(Status.class);
			query.select(root);
			//query.orderBy(builder.asc(root.get("name")));

			Query<Status> q = currentSession.createQuery(query);
			List<Status> data = q.getResultList();

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
	public void insert(Status status) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(status);

		tr.commit();

	}

	@Override
	public void update(Status status) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.update(status);

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
