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
import com.training.model.Anlage;

public class AnlageJDBCDAO implements AnlageDAO {

	private static final Logger logger = Logger.getLogger(AnlageJDBCDAO.class);

	private Session currentSession;
	private Transaction currentTransaction;

	@Override
	public void delete(Anlage data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(data);

		tr.commit();

	}

	@Override
	public Anlage get(int id) throws DAOException {
		Anlage data = currentSession.get(Anlage.class, id);
		return data;

	}

	@Override
	public List<Anlage> getAll() throws DAOException {
		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Anlage> query = builder.createQuery(Anlage.class);

			Root<Anlage> root = query.from(Anlage.class);
			query.select(root);

			Query<Anlage> q = currentSession.createQuery(query);
			List<Anlage> dataen = q.getResultList();

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
	
	
	public List<Anlage> getAnlagenFromStandort(String standort) throws DAOException {
		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			List<Anlage> data = currentSession
					.createNativeQuery("SELECT * " + "FROM anlage WHERE anlage.id IN "
							+ "(SELECT anlage.id FROM anlage, standort "
							+ "WHERE anlage.standort_id = standort.id and standort.name like '" + standort + "')  ORDER BY anlage.name ASC")
					.addEntity(Anlage.class).list();

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
	public void insert(Anlage data) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(data);

		tr.commit();

	}

	@Override
	public void update(Anlage data) throws DAOException {

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
