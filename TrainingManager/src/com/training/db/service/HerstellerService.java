package com.training.db.service;

import java.util.List;

import com.training.db.dao.HerstellerDAO;
import com.training.db.dao.HerstellerJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Hersteller;

public class HerstellerService {

	private static HerstellerJDBCDAO herstellerDAO;

	public HerstellerService() {

		herstellerDAO = new HerstellerJDBCDAO();

	}

	public void insert(Hersteller entity) {
		herstellerDAO.openCurrentSession();
		try {
			herstellerDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			herstellerDAO.closeCurrentSession();
		}
	}

	public void update(Hersteller entity) {
		herstellerDAO.openCurrentSession();
		try {
			herstellerDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			herstellerDAO.closeCurrentSession();
		}
	}

	public Hersteller findById(int id) {
		herstellerDAO.openCurrentSession();
		Hersteller data = null;
		try {
			data = herstellerDAO.get(id);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			herstellerDAO.closeCurrentSession();
		}
		return data;
	}

	public void delete(Hersteller data) {
		herstellerDAO.openCurrentSession();
		try {
			herstellerDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			herstellerDAO.closeCurrentSession();
		}
	}

	public List<Hersteller> findAll() {
		herstellerDAO.openCurrentSession();
		List<Hersteller> data = null;
		try {
			data = herstellerDAO.getAll();

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			herstellerDAO.closeCurrentSession();
		}
		return data;
	}

	public HerstellerDAO herstellerDAO() {
		return herstellerDAO;
	}

}
