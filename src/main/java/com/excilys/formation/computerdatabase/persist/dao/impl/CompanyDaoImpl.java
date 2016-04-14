package com.excilys.formation.computerdatabase.persist.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.persist.connection.ConnectionFactory;
import com.excilys.formation.computerdatabase.persist.dao.CompanyDao;

@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    private DataSource dataSource;
    private Logger daoLogger = LoggerFactory.getLogger(CompanyDaoImpl.class.getName());

    private CompanyDaoImpl() {
	daoLogger.info("Initialisation du DAO Company");
    }

    public ArrayList<Company> list() {
	String query = "Select id, name from company";
	ResultSet result;
	ArrayList<Company> companies;
	Statement stm = null;
	Connection connect = null;
	try {
	    connect = dataSource.getConnection();
	    stm = connect.createStatement();
	    result = stm.executeQuery(query);
	    companies = new ArrayList<>();
	    while (result.next()) {
		Company c1 = new Company(result.getString("name"), result.getInt("id"));
		companies.add(c1);
	    }
	} catch (SQLException e) {
	    daoLogger.error(e.toString());
	    return null;
	} finally {
	    ConnectionFactory.closeConnection(connect, stm);
	}
	return companies;
    }

    @Override
    public Company find(long id) {
	String query = "Select id, name from company where id=" + id;
	ResultSet result;
	Company company = null;
	Connection con = null;
	Statement stm = null;
	try {
	    con = dataSource.getConnection();
	    stm = con.createStatement();
	    result = stm.executeQuery(query);
	    if (result.next()) {
		company = new Company(result.getString("name"), result.getInt("id"));
	    }
	} catch (SQLException e) {
	    daoLogger.error(e.toString());
	    return null;
	} finally {
	    ConnectionFactory.closeConnection(con, stm);
	}
	return company;
    }

    public int count() {
	Connection con = null;
	String query = "SELECT count(*) from company";
	ResultSet result;
	Statement stm = null;
	try {
	    con = dataSource.getConnection();
	    stm = con.createStatement();
	    result = stm.executeQuery(query);
	    if (result.first()) {
		return result.getInt(1);
	    } else {
		return 0;
	    }
	} catch (SQLException e) {
	    daoLogger.error(e.toString());
	    return 0;
	} finally {
	    try {
		if (con.getAutoCommit() == true) {
		    ConnectionFactory.closeConnection(con, stm);
		}
	    } catch (Exception e) {
		daoLogger.error(e.toString());
	    }
	}
    }

    @Override
    public List<Company> findAll(long index, int nbrElement) {
	Connection con = null;
	String query = "Select * from company" + " LIMIT " + index + "," + nbrElement;
	ResultSet result;
	ArrayList<Company> companies;
	Statement stm = null;
	try {
	    con = dataSource.getConnection();
	    stm = con.createStatement();
	    result = stm.executeQuery(query);
	    companies = new ArrayList<>();
	    while (result.next()) {
		Company c1 = new Company(result.getString("name"), result.getInt("id"));
		companies.add(c1);
		LoggerFactory.getLogger(this.getClass());
	    }
	} catch (SQLException e) {
	    daoLogger.error(e.toString());
	    return null;
	} finally {
	    try {
		if (con.getAutoCommit() == true) {
		    ConnectionFactory.closeConnection(con, stm);
		}
	    } catch (Exception e) {
		daoLogger.error(e.toString());
	    }
	}
	return companies;
    }

    public Company findByName(String id) {
	Connection con = null;
	System.out.println("connection recupereé" + con);
	String query = "Select id, name from company where name=" + id;
	ResultSet result;
	Company company = null;
	Statement stm = null;
	try {
	    con = dataSource.getConnection();
	    stm = con.createStatement();
	    result = stm.executeQuery(query);
	    if (result.next()) {
		company = new Company(result.getString("name"), result.getInt("id"));
	    }
	} catch (SQLException e) {
	    daoLogger.error(e.getMessage());
	    return null;
	} finally {
	    try {
		if (con.getAutoCommit() == true) {
		    ConnectionFactory.closeConnection(con, stm);
		}
	    } catch (Exception e) {
		daoLogger.error(e.toString());
	    }
	}
	return company;
    }

    @Override
    @Transactional()
    public boolean delete(Company c) {
	Connection con = null;
	try {
	    con = dataSource.getConnection();
	    Statement stm = con.createStatement();
	    stm.executeUpdate("DELETE from company where id=" + c.getId());
	} catch (SQLException e) {
	    daoLogger.error(e.toString());
	    return false;
	}
	return true;
    }
}