package com.training.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "standort")
public class Standort {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();

	private List<Abteilung> abteilungen;
	private List<Anlage> anlagen;

	public IntegerProperty idProperty() {
		return this.id;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.idProperty().get();
	}

	public void setId(final int id) {
		this.idProperty().set(id);
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public String getName() {
		return this.nameProperty().get();
	}

	public void setName(final String name) {
		this.nameProperty().set(name);
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "standort", cascade = CascadeType.ALL)
	public List<Abteilung> getAbteilungen() {
		return abteilungen;
	}

	public void setAbteilungen(List<Abteilung> abteilungen) {
		this.abteilungen = abteilungen;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "standort", cascade = CascadeType.ALL)
	public List<Anlage> getAnlagen() {
		return anlagen;
	}

	public void setAnlagen(List<Anlage> anlagen) {
		this.anlagen = anlagen;
	}

}
