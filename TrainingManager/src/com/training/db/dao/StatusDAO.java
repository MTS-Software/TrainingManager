package com.training.db.dao;

import java.util.List;

import com.training.db.util.DAOException;
import com.training.model.Status;

public interface StatusDAO {

	public void delete(Status status) throws DAOException;

	public Status get(int statusId) throws DAOException;

	public List<Status> getAll() throws DAOException;

	public void insert(Status status) throws DAOException;

	public void update(Status status) throws DAOException;

}