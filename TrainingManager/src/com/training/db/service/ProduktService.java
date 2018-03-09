package com.training.db.service;

import java.util.List;

import org.hibernate.Hibernate;

import com.training.db.dao.ProduktDAO;
import com.training.db.dao.ProduktJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Produkt;

public class ProduktService {

	private static ProduktJDBCDAO produktDAO;

	public ProduktService() {

		produktDAO = new ProduktJDBCDAO();

	}

	public void insert(Produkt entity) {
		produktDAO.openCurrentSession();
		try {
			produktDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			produktDAO.closeCurrentSession();
		}
	}

	public void update(Produkt entity) {
		produktDAO.openCurrentSession();
		try {
			produktDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			produktDAO.closeCurrentSession();
		}
	}

	public Produkt findById(int id) {
		produktDAO.openCurrentSession();
		Produkt data = null;
		try {
			data = produktDAO.get(id);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			produktDAO.closeCurrentSession();
		}
		return data;

	}

	public void delete(Produkt data) {
		produktDAO.openCurrentSession();
		try {
			produktDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			produktDAO.closeCurrentSession();
		}
	}

	public List<Produkt> findAll() {
		produktDAO.openCurrentSession();
		List<Produkt> data = null;
		try {
			data = produktDAO.getAll();

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			produktDAO.closeCurrentSession();
		}
		return data;

	}

	public List<Produkt> findProduktWithKategorieWithHersteller() {
		produktDAO.openCurrentSession();
		List<Produkt> data = null;
		try {
			data = produktDAO.getAll();

			for (Produkt produkt : data) {

				Hibernate.initialize(produkt.getKategorie());
				Hibernate.initialize(produkt.getHersteller());

			}

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			produktDAO.closeCurrentSession();
		}
		return data;

	}

	public ProduktDAO produktDAO() {
		return produktDAO;
	}

}
