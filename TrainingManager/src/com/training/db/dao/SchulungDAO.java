package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Schulung;

public interface SchulungDAO {

	public void delete(Schulung schulung) throws DAOException;

	public Schulung get(int schulungId) throws DAOException;

	public List<Schulung> getAll() throws DAOException;

	public void insert(Schulung schulung) throws DAOException;

	public void update(Schulung schulung) throws DAOException;

}