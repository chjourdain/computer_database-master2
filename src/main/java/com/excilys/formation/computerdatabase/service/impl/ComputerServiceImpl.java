package com.excilys.formation.computerdatabase.service.impl;

import java.util.List;

import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.persist.connection.ConnectionFactory;
import com.excilys.formation.computerdatabase.persist.dao.impl.ComputerDaoImpl;
import com.excilys.formation.computerdatabase.service.ComputerService;

public class ComputerServiceImpl implements ComputerService {
	private static ComputerServiceImpl instance;
	private ComputerDaoImpl computerDao = ComputerDaoImpl.INSTANCE;

	private ComputerServiceImpl() {
	}

	public static ComputerService getComputerService() {
		if (instance == null) {
			instance = new ComputerServiceImpl();
		}
		return instance;
	}

	public List<Computer> listComputer(int page) {
		return computerDao.list(page);

	}

	public int count() {
		return computerDao.count();
	}

	public Computer find(int id) {
		if (id < 0) {
			return null;
		}
		return computerDao.find(id);
	}

	public Computer create(Computer c) {
		if (c == null) {
			return null;
		}
		return computerDao.create(c);

	}

	public Computer update(Computer c) {
		if (c == null) {
			return null;
		}
		return computerDao.update(c);

	}

	public void delete(Computer c) {
		if (c != null) {
			computerDao.delete(c);
		}

	}

	@Override
	public List<Computer> findAll(Long index, int nbrElement) {
		if (index < 0 || nbrElement <= 0) {
			return null;
		}
		return computerDao.findAll(index, nbrElement);
	}

}