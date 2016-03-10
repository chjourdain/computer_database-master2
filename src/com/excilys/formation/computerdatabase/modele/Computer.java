package com.excilys.formation.computerdatabase.modele;

import java.time.LocalDate;

/**
 * Class de modélisation d'un ordianteur
 * @author excilys
 *
 */
public class Computer {
	
	protected int id;
	protected String name;
	protected LocalDate introduced;
	public void setName(String name) {
		this.name = name;
	}
	protected LocalDate discontinued;
	protected String companieName;
	
	public Computer(String pName){
		
		this.name=pName;
		
	}
	
	public Computer(int pId, String pName){
		this.id=pId;
		this.name=pName;
		
	}
	public Computer(String pName, LocalDate pIntroduced,LocalDate pDiscontinued, String pCompanieName  ){
		this.name=pName;
		this.introduced=pIntroduced;
		this.discontinued=pDiscontinued;
		this.companieName=pCompanieName;
		
	}
	
	public Computer(int pId, String pName, LocalDate pIntroduced,LocalDate pDiscontinued, String pCompanieName  ){
		this.id=pId;
		this.name=pName;
		this.introduced=pIntroduced;
		this.discontinued=pDiscontinued;
		this.companieName=pCompanieName;
		
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompanieName() {
		return companieName;
	}

	public void setCompanieName(String companieName) {
		this.companieName = companieName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Ordianteur : "+name);
		sb.append((companieName==null) ? "" : ",  de marque : "+companieName);
		sb.append((introduced==null) ? "" : ",  introduit le : "+introduced);
		sb.append((discontinued==null) ? "" : ",   Fin de production : "+discontinued);
		sb.append(", ID : ").append(id);
		
		return sb.toString();
	}
	
	
}