package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Anlage;

public interface AnlageDAO {

	public void delete(Anlage data) throws DAOException;

	public Anlage get(int dataId) throws DAOException;

	public List<Anlage> getAll() throws DAOException;

	public void insert(Anlage data) throws DAOException;

	public void update(Anlage data) throws DAOException;

}