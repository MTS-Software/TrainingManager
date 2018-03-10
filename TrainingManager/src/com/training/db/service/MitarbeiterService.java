package com.training.db.service;

import java.util.List;

import org.hibernate.Hibernate;

import com.training.db.dao.MitarbeiterDAO;
import com.training.db.dao.MitarbeiterJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Mitarbeiter;
import com.training.model.Standort;

public class MitarbeiterService {

	private static MitarbeiterJDBCDAO mitarbeiterDAO;

	public MitarbeiterService() {

		mitarbeiterDAO = new MitarbeiterJDBCDAO();

	}

	public void insert(Mitarbeiter entity) {
		mitarbeiterDAO.openCurrentSession();
		try {
			mitarbeiterDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mitarbeiterDAO.closeCurrentSession();
		}
	}

	public void update(Mitarbeiter entity) {
		mitarbeiterDAO.openCurrentSession();
		try {
			mitarbeiterDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mitarbeiterDAO.closeCurrentSession();
		}
	}

	public Mitarbeiter findById(int id) {
		mitarbeiterDAO.openCurrentSession();
		Mitarbeiter data = null;
		try {
			data = mitarbeiterDAO.get(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mitarbeiterDAO.closeCurrentSession();
		}
		return data;
	}

	public void delete(Mitarbeiter data) {
		mitarbeiterDAO.openCurrentSession();
		try {
			mitarbeiterDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mitarbeiterDAO.closeCurrentSession();
		}
	}

	public List<Mitarbeiter> findAll() {
		mitarbeiterDAO.openCurrentSession();
		List<Mitarbeiter> data = null;
		try {
			data = mitarbeiterDAO.getAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mitarbeiterDAO.closeCurrentSession();
		}
		return data;
	}

	public List<Mitarbeiter> findMitarbeiterWithAbteilungWithStandort() {
		mitarbeiterDAO.openCurrentSession();
		List<Mitarbeiter> data = null;
		try {
			data = mitarbeiterDAO.getAll();
			for (Mitarbeiter mit : data) {

				Hibernate.initialize(mit.getAbteilung().getStandort());

			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mitarbeiterDAO.closeCurrentSession();
		}
		return data;
	}
	
	public List<Mitarbeiter> findMitarbeiterFromStandortWithAbteilung(String standort) {
		mitarbeiterDAO.openCurrentSession();
		List<Mitarbeiter> data = null;
		try {
			data = mitarbeiterDAO.getMitarbeiterFromStandort(standort);
			for (Mitarbeiter mit : data) {

				Hibernate.initialize(mit.getAbteilung().getStandort());

			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mitarbeiterDAO.closeCurrentSession();
		}
		return data;
	}

	public List<Mitarbeiter> findMitarbeiterWithAbteilung() {
		mitarbeiterDAO.openCurrentSession();
		List<Mitarbeiter> data = null;
		try {
			data = mitarbeiterDAO.getAll();
			for (Mitarbeiter mit : data) {

				Hibernate.initialize(mit.getAbteilung());

			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mitarbeiterDAO.closeCurrentSession();
		}
		return data;
	}

	public List<Mitarbeiter> findMitarbeiterWithAnlagen() {
		mitarbeiterDAO.openCurrentSession();
		List<Mitarbeiter> data = null;
		try {
			data = mitarbeiterDAO.getAll();
			for (Mitarbeiter mit : data) {

				Hibernate.initialize(mit.getAnlagen());

			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mitarbeiterDAO.closeCurrentSession();
		}
		return data;
	}

	public MitarbeiterDAO mitarbeiterDAO() {
		return mitarbeiterDAO;
	}

}
