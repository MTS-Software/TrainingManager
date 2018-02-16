package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Hersteller;

public interface HerstellerDAO {

	public void delete(Hersteller data) throws DAOException;

	public Hersteller get(int dataId) throws DAOException;

	public List<Hersteller> getAll() throws DAOException;

	public void insert(Hersteller data) throws DAOException;

	public void update(Hersteller data) throws DAOException;

}