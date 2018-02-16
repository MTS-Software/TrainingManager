package com.training.db.service;

import java.util.List;

import com.training.db.dao.LevelDAO;
import com.training.db.dao.LevelJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Level;

public class LevelService {

	private static LevelJDBCDAO levelDAO;

	public LevelService() {

		levelDAO = new LevelJDBCDAO();

	}

	public void insert(Level entity) {
		levelDAO.openCurrentSession();
		try {
			levelDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			levelDAO.closeCurrentSession();
		}
	}

	public void update(Level entity) {
		levelDAO.openCurrentSession();
		try {
			levelDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			levelDAO.closeCurrentSession();
		}
	}

	public Level findById(int id) {
		levelDAO.openCurrentSession();
		Level data = null;
		try {
			data = levelDAO.get(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			levelDAO.closeCurrentSession();
		}
		return data;
	}

	public void delete(Level data) {
		levelDAO.openCurrentSession();
		try {
			levelDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			levelDAO.closeCurrentSession();
		}
	}

	public List<Level> findAll() {
		levelDAO.openCurrentSession();
		List<Level> data = null;
		try {
			data = levelDAO.getAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			levelDAO.closeCurrentSession();
		}
		return data;
	}

	public LevelDAO levelDAO() {
		return levelDAO;
	}

}
