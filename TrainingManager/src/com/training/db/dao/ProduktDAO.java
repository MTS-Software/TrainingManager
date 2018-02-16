package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Produkt;

public interface ProduktDAO {

	public void delete(Produkt data) throws DAOException;

	public Produkt get(int dataId) throws DAOException;

	public List<Produkt> getAll() throws DAOException;

	public void insert(Produkt data) throws DAOException;

	public void update(Produkt data) throws DAOException;

}