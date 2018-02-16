package com.training.db.service;

import java.util.List;

import com.training.db.dao.KategorieDAO;
import com.training.db.dao.KategorieJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Kategorie;

public class KategorieService {

	private static KategorieJDBCDAO kategorieDAO;

	public KategorieService() {

		kategorieDAO = new KategorieJDBCDAO();

	}

	public void insert(Kategorie entity) {
		kategorieDAO.openCurrentSession();
		try {
			kategorieDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			kategorieDAO.closeCurrentSession();
		}
	}

	public void update(Kategorie entity) {
		kategorieDAO.openCurrentSession();
		try {
			kategorieDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			kategorieDAO.closeCurrentSession();
		}
	}

	public Kategorie findById(int id) {
		kategorieDAO.openCurrentSession();
		Kategorie data = null;
		try {
			data = kategorieDAO.get(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			kategorieDAO.closeCurrentSession();
		}
		return data;
	}

	public void delete(Kategorie data) {
		kategorieDAO.openCurrentSession();
		try {
			kategorieDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			kategorieDAO.closeCurrentSession();
		}
	}

	public List<Kategorie> findAll() {
		kategorieDAO.openCurrentSession();
		List<Kategorie> data = null;
		try {
			data = kategorieDAO.getAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			kategorieDAO.closeCurrentSession();
		}
		return data;
	}

	public KategorieDAO kategorieDAO() {
		return kategorieDAO;
	}

}
