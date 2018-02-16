package com.training.db.service;

import java.util.List;

import com.training.db.dao.AbteilungDAO;
import com.training.db.dao.AbteilungJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Abteilung;

public class AbteilungService {

	private static AbteilungJDBCDAO abteilungDAO;

	public AbteilungService() {

		abteilungDAO = new AbteilungJDBCDAO();

	}

	public void insert(Abteilung entity) {
		abteilungDAO.openCurrentSession();
		try {
			abteilungDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			abteilungDAO.closeCurrentSession();
		}
	}

	public void update(Abteilung entity) {
		abteilungDAO.openCurrentSession();
		try {
			abteilungDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			abteilungDAO.closeCurrentSession();
		}
	}

	public Abteilung findById(int id) {
		abteilungDAO.openCurrentSession();
		Abteilung data = null;
		try {
			data = abteilungDAO.get(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			abteilungDAO.closeCurrentSession();
		}
		return data;
	}

	public void delete(Abteilung data) {
		abteilungDAO.openCurrentSession();
		try {
			abteilungDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			abteilungDAO.closeCurrentSession();
		}
	}

	public List<Abteilung> findAll() {
		abteilungDAO.openCurrentSession();
		List<Abteilung> data = null;
		try {
			data = abteilungDAO.getAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			abteilungDAO.closeCurrentSession();
		}
		return data;
	}

	public AbteilungDAO abteilungDAO() {
		return abteilungDAO;
	}

}
