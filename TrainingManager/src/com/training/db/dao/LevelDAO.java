package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Level;

public interface LevelDAO {

	public void delete(Level level) throws DAOException;

	public Level get(int levelId) throws DAOException;

	public List<Level> getAll() throws DAOException;

	public void insert(Level level) throws DAOException;

	public void update(Level level) throws DAOException;

}