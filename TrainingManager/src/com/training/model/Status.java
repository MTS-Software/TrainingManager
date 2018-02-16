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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "status")
public class Status {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();

	private List<Schulung> schulungen;

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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status", cascade = CascadeType.ALL)
	public List<Schulung> getSchulungen() {
		return schulungen;
	}

	public void setSchulungen(List<Schulung> schulungen) {
		this.schulungen = schulungen;
	}

	@Override
	public String toString() {
		return getName();
	}

}
