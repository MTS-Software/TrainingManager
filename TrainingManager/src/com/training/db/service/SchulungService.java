package com.training.db.service;

import java.util.List;

import com.training.db.dao.SchulungDAO;
import com.training.db.dao.SchulungJDBCDAO;
import com.training.db.util.DAOException;
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

	public SchulungDAO schulungDAO() {
		return schulungDAO;
	}

}
