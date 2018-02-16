package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Abteilung;

public interface AbteilungDAO {

	public void delete(Abteilung abteilung) throws DAOException;

	public Abteilung get(int abteilungId) throws DAOException;

	public List<Abteilung> getAll() throws DAOException;

	public void insert(Abteilung abteilung) throws DAOException;

	public void update(Abteilung abteilung) throws DAOException;

}