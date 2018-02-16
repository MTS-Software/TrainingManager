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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "anlage")
public class Anlage {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Standort> standort = new SimpleObjectProperty<>();

	private List<Produkt> produkte;
	private List<Mitarbeiter> mitarbeiter;

	private BooleanProperty active = new SimpleBooleanProperty();

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

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinTable(name = "anlage_produkt", joinColumns = { @JoinColumn(name = "anlage_id") }, inverseJoinColumns = {
			@JoinColumn(name = "produkt_id") })
	public List<Produkt> getProdukte() {
		return produkte;
	}

	public void setProdukte(List<Produkt> produkte) {
		this.produkte = produkte;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinTable(name = "anlage_mitarbeiter", joinColumns = { @JoinColumn(name = "anlage_id") }, inverseJoinColumns = {
			@JoinColumn(name = "mitarbeiter_id") })
	public List<Mitarbeiter> getMitarbeiter() {
		return mitarbeiter;
	}

	public void setMitarbeiter(List<Mitarbeiter> mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public BooleanProperty activeProperty() {
		return this.active;
	}

	@Transient
	public boolean isActive() {
		return this.activeProperty().get();
	}

	public void setActive(final boolean active) {
		this.activeProperty().set(active);
	}

}
