package com.training.db.service;

import java.util.List;

import com.training.db.dao.StatusDAO;
import com.training.db.dao.StatusJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Status;

public class StatusService {

	private static StatusJDBCDAO statusDAO;

	public StatusService() {

		statusDAO = new StatusJDBCDAO();

	}

	public void insert(Status entity) {
		statusDAO.openCurrentSession();
		try {
			statusDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statusDAO.closeCurrentSession();
		}
	}

	public void update(Status entity) {
		statusDAO.openCurrentSession();
		try {
			statusDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statusDAO.closeCurrentSession();
		}
	}

	public Status findById(int id) {
		statusDAO.openCurrentSession();
		Status data = null;
		try {
			data = statusDAO.get(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statusDAO.closeCurrentSession();
		}
		return data;
	}

	public void delete(Status data) {
		statusDAO.openCurrentSession();
		try {
			statusDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statusDAO.closeCurrentSession();
		}
	}

	public List<Status> findAll() {
		statusDAO.openCurrentSession();
		List<Status> data = null;
		try {
			data = statusDAO.getAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statusDAO.closeCurrentSession();
		}
		return data;
	}

	public StatusDAO statusDAO() {
		return statusDAO;
	}

}
