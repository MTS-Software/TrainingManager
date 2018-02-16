package com.training.db.service;

import java.util.List;

import com.training.db.dao.ProduktDAO;
import com.training.db.dao.ProduktJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Produkt;

public class ProduktService {

	private static ProduktJDBCDAO technologieDAO;

	public ProduktService() {

		technologieDAO = new ProduktJDBCDAO();

	}

	public void insert(Produkt entity) {
		technologieDAO.openCurrentSession();
		try {
			technologieDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			technologieDAO.closeCurrentSession();
		}
	}

	public void update(Produkt entity) {
		technologieDAO.openCurrentSession();
		try {
			technologieDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			technologieDAO.closeCurrentSession();
		}
	}

	public Produkt findById(int id) {
		technologieDAO.openCurrentSession();
		Produkt data = null;
		try {
			data = technologieDAO.get(id);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			technologieDAO.closeCurrentSession();
		}
		return data;

	}

	public void delete(Produkt data) {
		technologieDAO.openCurrentSession();
		try {
			technologieDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			technologieDAO.closeCurrentSession();
		}
	}

	public List<Produkt> findAll() {
		technologieDAO.openCurrentSession();
		List<Produkt> data = null;
		try {
			data = technologieDAO.getAll();

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			technologieDAO.closeCurrentSession();
		}
		return data;

	}

	public ProduktDAO produktDAO() {
		return technologieDAO;
	}

}
