package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Kategorie;

public interface KategorieDAO {

	public void delete(Kategorie data) throws DAOException;

	public Kategorie get(int id) throws DAOException;

	public List<Kategorie> getAll() throws DAOException;

	public void insert(Kategorie data) throws DAOException;

	public void update(Kategorie data) throws DAOException;

}