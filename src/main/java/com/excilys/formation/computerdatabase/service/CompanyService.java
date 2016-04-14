package com.excilys.formation.computerdatabase.service;

import com.excilys.formation.computerdatabase.model.Company;

public interface CompanyService extends GenericService<Company> {

    Company findByName(String companyName);
}
