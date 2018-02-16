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
import com.training.model.Schulung;

public class SchulungJDBCDAO implements SchulungDAO {

	private static final Logger logger = Logger.getLogger(SchulungJDBCDAO.class);
	private Session currentSession;

	@Override
	public void delete(Schulung schulung) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(schulung);

		tr.commit();

	}

	@Override
	public Schulung get(int id) throws DAOException {
		Schulung data = currentSession.get(Schulung.class, id);
		return data;

	}

	@Override
	public List<Schulung> getAll() throws DAOException {
		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Schulung> query = builder.createQuery(Schulung.class);

			Root<Schulung> root = query.from(Schulung.class);
			query.select(root);
			query.orderBy(builder.desc(root.get("end")));

			Query<Schulung> q = currentSession.createQuery(query);
			List<Schulung> schulungen = q.getResultList();

			transaction.commit();

			return schulungen;

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
	public void insert(Schulung schulung) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(schulung);

		tr.commit();

	}

	@Override
	public void update(Schulung schulung) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.update(schulung);

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
