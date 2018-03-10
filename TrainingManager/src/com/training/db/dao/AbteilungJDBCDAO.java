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
import com.training.model.Abteilung;
import com.training.model.Mitarbeiter;

public class AbteilungJDBCDAO implements AbteilungDAO {

	private static final Logger logger = Logger.getLogger(AbteilungJDBCDAO.class);

	private Session currentSession;
	private Transaction currentTransaction;

	@Override
	public void delete(Abteilung abteilung) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.delete(abteilung);

		tr.commit();

	}

	@Override
	public Abteilung get(int id) throws DAOException {

		Abteilung data = currentSession.get(Abteilung.class, id);
		return data;

	}

	@Override
	public List<Abteilung> getAll() throws DAOException {
		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			CriteriaQuery<Abteilung> query = builder.createQuery(Abteilung.class);

			Root<Abteilung> root = query.from(Abteilung.class);
			query.select(root);

			Query<Abteilung> q = currentSession.createQuery(query);
			List<Abteilung> abteilungen = q.getResultList();

			transaction.commit();

			return abteilungen;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {

		}
		return null;

	}

	public List<Abteilung> getAbteilungenFromStandort(String standort) throws DAOException {
		Transaction transaction = null;

		try {

			transaction = currentSession.beginTransaction();

			List<Abteilung> data = currentSession
					.createNativeQuery("SELECT * " + "FROM abteilung WHERE abteilung.id IN "
							+ "(SELECT abteilung.id FROM abteilung, standort "
							+ "WHERE abteilung.standort_id = standort.id and standort.name like '" + standort + "')")
					.addEntity(Abteilung.class).list();

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
	public void insert(Abteilung abteilung) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.save(abteilung);

		tr.commit();

	}

	@Override
	public void update(Abteilung abteilung) throws DAOException {

		Transaction tr = currentSession.beginTransaction();

		currentSession.update(abteilung);

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
