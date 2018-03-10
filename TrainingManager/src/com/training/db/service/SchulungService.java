package com.training.db.service;

import java.util.List;

import org.hibernate.Hibernate;

import com.training.db.dao.SchulungDAO;
import com.training.db.dao.SchulungJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Mitarbeiter;
import com.training.model.Schulung;

public class SchulungService {

	private static SchulungJDBCDAO schulungDAO;

	public SchulungService() {

		schulungDAO = new SchulungJDBCDAO();

	}

	public void insert(Schulung entity) {
		schulungDAO.openCurrentSession();
		try {
			schulungDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			schulungDAO.closeCurrentSession();
		}
	}

	public void update(Schulung entity) {
		schulungDAO.openCurrentSession();
		try {
			schulungDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			schulungDAO.closeCurrentSession();
		}
	}

	public Schulung findById(int id) {
		schulungDAO.openCurrentSession();
		Schulung data = null;
		try {
			data = schulungDAO.get(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			schulungDAO.closeCurrentSession();
		}
		return data;
	}

	public void delete(Schulung data) {
		schulungDAO.openCurrentSession();
		try {
			schulungDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			schulungDAO.closeCurrentSession();
		}
	}

	public List<Schulung> findAll() {
		schulungDAO.openCurrentSession();
		List<Schulung> data = null;
		try {
			data = schulungDAO.getAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			schulungDAO.closeCurrentSession();
		}
		return data;
	}

	public List<Schulung> findSchulungenFromStandortWithMitarbeiterWithLevelWithStatusWithProdukt(String standort) {
		schulungDAO.openCurrentSession();
		List<Schulung> data = null;
		try {
			data = schulungDAO.getSchulungenFromStandort(standort);

			for (Schulung s : data) {
				Hibernate.initialize(s.getMitarbeiter());
				Hibernate.initialize(s.getLevel());
				Hibernate.initialize(s.getStatus());
				if (s.getProdukt() != null)
					Hibernate.initialize(s.getProdukt().getHersteller());

			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			schulungDAO.closeCurrentSession();
		}
		return data;
	}

	public List<Schulung> findSchulungenWithMitarbeiterWithLevelWithStatusWithProdukt() {
		schulungDAO.openCurrentSession();
		List<Schulung> data = null;
		try {
			data = schulungDAO.getAll();

			for (Schulung s : data) {
				Hibernate.initialize(s.getMitarbeiter());
				Hibernate.initialize(s.getLevel());
				Hibernate.initialize(s.getStatus());
				if (s.getProdukt() != null)
					Hibernate.initialize(s.getProdukt().getHersteller());

			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			schulungDAO.closeCurrentSession();
		}
		return data;
	}

	public SchulungDAO schulungDAO() {
		return schulungDAO;
	}

}
