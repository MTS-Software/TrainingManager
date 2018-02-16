package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Mitarbeiter;

public interface MitarbeiterDAO {

	public void delete(Mitarbeiter mitarbeiter) throws DAOException;

	public Mitarbeiter get(int mitarbeiterId) throws DAOException;

	public List<Mitarbeiter> getAll() throws DAOException;

	public void insert(Mitarbeiter mitarbeiter) throws DAOException;

	public void update(Mitarbeiter mitarbeiter) throws DAOException;

}