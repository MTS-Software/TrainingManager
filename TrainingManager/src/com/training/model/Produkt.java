package com.training.model;

import java.util.List;

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
import javax.persistence.Transient;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "produkt")
public class Produkt {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Kategorie> kategorie = new SimpleObjectProperty<>();
	private ObjectProperty<Hersteller> hersteller = new SimpleObjectProperty<>();

	private List<Schulung> schulungen;

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

	public ObjectProperty<Hersteller> herstellerProperty() {
		return this.hersteller;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hersteller_id")
	public Hersteller getHersteller() {
		return this.herstellerProperty().get();
	}

	public void setHersteller(final Hersteller hersteller) {
		this.herstellerProperty().set(hersteller);
	}

	public ObjectProperty<Kategorie> kategorieProperty() {
		return this.kategorie;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kategorie_id")
	public Kategorie getKategorie() {
		return this.kategorieProperty().get();
	}

	public void setKategorie(final Kategorie kategorie) {
		this.kategorieProperty().set(kategorie);
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produkt")
	public List<Schulung> getSchulungen() {
		return schulungen;
	}

	public void setSchulungen(List<Schulung> schulungen) {
		this.schulungen = schulungen;
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
