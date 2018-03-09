package com.training.db.service;

import java.util.List;

import org.hibernate.Hibernate;

import com.training.db.dao.StandortDAO;
import com.training.db.dao.StandortJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Abteilung;
import com.training.model.Standort;

public class StandortService {

	private static StandortJDBCDAO standortDAO;

	public StandortService() {

		standortDAO = new StandortJDBCDAO();

	}

	public void insert(Standort entity) {
		standortDAO.openCurrentSession();
		try {
			standortDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			standortDAO.closeCurrentSession();
		}
	}

	public void update(Standort entity) {
		standortDAO.openCurrentSession();
		try {
			standortDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			standortDAO.closeCurrentSession();
		}
	}

	public Standort findById(int id) {
		standortDAO.openCurrentSession();
		Standort data = null;
		try {
			data = standortDAO.get(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			standortDAO.closeCurrentSession();
		}
		return data;
	}

	public void delete(Standort data) {
		standortDAO.openCurrentSession();
		try {
			standortDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			standortDAO.closeCurrentSession();
		}
	}

	public List<Standort> findAll() {
		standortDAO.openCurrentSession();
		List<Standort> data = null;
		try {
			data = standortDAO.getAll();

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			standortDAO.closeCurrentSession();
		}
		return data;
	}

	public List<Standort> findStandortWithAbteilungen() {
		standortDAO.openCurrentSession();
		List<Standort> data = null;
		try {
			data = standortDAO.getAll();
			for (Standort st : data)
				Hibernate.initialize(st.getAbteilungen());

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			standortDAO.closeCurrentSession();
		}
		return data;
	}
	
	public List<Standort> findStandortWithAnlagen() {
		standortDAO.openCurrentSession();
		List<Standort> data = null;
		try {
			data = standortDAO.getAll();
			for (Standort st : data)
				Hibernate.initialize(st.getAnlagen());

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			standortDAO.closeCurrentSession();
		}
		return data;
	}

	public StandortDAO standortDAO() {
		return standortDAO;
	}

}
