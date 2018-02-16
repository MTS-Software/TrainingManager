package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Standort;

public interface StandortDAO {

	public void delete(Standort standort) throws DAOException;

	public Standort get(int id) throws DAOException;

	public List<Standort> getAll() throws DAOException;

	public void insert(Standort standort) throws DAOException;

	public void update(Standort standort) throws DAOException;

}