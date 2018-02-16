package com.training.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "abteilung")
public class Abteilung {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty kostenstelle = new SimpleStringProperty();
	private ObjectProperty<Standort> standort = new SimpleObjectProperty<>();

	private List<Mitarbeiter> mitarbeiter;

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

	public StringProperty kostenstelleProperty() {
		return this.kostenstelle;
	}

	public String getKostenstelle() {
		return this.kostenstelleProperty().get();
	}

	public void setKostenstelle(final String kostenstelle) {
		this.kostenstelleProperty().set(kostenstelle);
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "abteilung", cascade = CascadeType.ALL)
	public List<Mitarbeiter> getMitarbeiter() {
		return mitarbeiter;
	}

	public void setMitarbeiter(List<Mitarbeiter> mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public ObjectProperty<Standort> standortProperty() {
		return this.standort;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "standort_id")
	public Standort getStandort() {
		return this.standortProperty().get();
	}

	public void setStandort(final Standort standort) {
		this.standortProperty().set(standort);
	}

}
