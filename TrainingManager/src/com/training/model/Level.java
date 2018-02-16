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
@Table(name = "level")

public class Level {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private IntegerProperty level = new SimpleIntegerProperty();

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

	public int getLevel() {
		return this.levelProperty().get();
	}

	public void setLevel(final int level) {
		this.levelProperty().set(level);
	}

	public IntegerProperty levelProperty() {
		return this.level;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "level", cascade = CascadeType.ALL)
	public List<Schulung> getSchulungen() {
		return schulungen;
	}

	public void setSchulungen(List<Schulung> schulungen) {
		this.schulungen = schulungen;
	}

}
