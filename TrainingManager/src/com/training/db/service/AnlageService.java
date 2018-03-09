package com.training.db.service;

import java.util.List;

import org.hibernate.Hibernate;

import com.training.db.dao.AnlageDAO;
import com.training.db.dao.AnlageJDBCDAO;
import com.training.db.util.DAOException;
import com.training.model.Anlage;
import com.training.model.Produkt;

public class AnlageService {

	private static AnlageJDBCDAO anlageDAO;

	public AnlageService() {

		anlageDAO = new AnlageJDBCDAO();

	}

	public void insert(Anlage entity) {
		anlageDAO.openCurrentSession();
		try {
			anlageDAO.insert(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			anlageDAO.closeCurrentSession();
		}
	}

	public void update(Anlage entity) {
		anlageDAO.openCurrentSession();
		try {
			anlageDAO.update(entity);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			anlageDAO.closeCurrentSession();
		}
	}

	public Anlage findById(int id) {
		anlageDAO.openCurrentSession();
		Anlage data = null;
		try {
			data = anlageDAO.get(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			anlageDAO.closeCurrentSession();
		}
		return data;
	}

	public void delete(Anlage data) {
		anlageDAO.openCurrentSession();
		try {
			anlageDAO.delete(data);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			anlageDAO.closeCurrentSession();
		}

	}

	public List<Anlage> findAll() {
		anlageDAO.openCurrentSession();
		List<Anlage> data = null;
		try {
			data = anlageDAO.getAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			anlageDAO.closeCurrentSession();
		}
		return data;
	}
	
	public List<Anlage> findAnlagenWithStandort() {
		anlageDAO.openCurrentSession();
		List<Anlage> data = null;
		try {
			data = anlageDAO.getAll();

			for (Anlage anl : data)
				Hibernate.initialize(anl.getStandort());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			anlageDAO.closeCurrentSession();
		}
		return data;
	}

	public List<Anlage> findAnlagenWithProdukteWithHerstellerWithKategorie() {
		anlageDAO.openCurrentSession();
		List<Anlage> data = null;
		try {
			data = anlageDAO.getAll();

			for (Anlage anl : data) {
				Hibernate.initialize(anl.getProdukte());
				
				for(Produkt prod : anl.getProdukte()) {
					Hibernate.initialize(prod.getKategorie());
					Hibernate.initialize(prod.getHersteller().getHerstellerProdukte());
					
					
				}
				
				
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			anlageDAO.closeCurrentSession();
		}
		return data;
	}

	public AnlageDAO anlageDAO() {
		return anlageDAO;
	}

}
