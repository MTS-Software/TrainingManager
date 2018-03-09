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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "mitarbeiter")
public class Mitarbeiter {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty vorname = new SimpleStringProperty();
	private StringProperty nachname = new SimpleStringProperty();
	private StringProperty persnr = new SimpleStringProperty();
	private StringProperty telephone = new SimpleStringProperty();
	private StringProperty taetigkeit = new SimpleStringProperty();

	private byte[] foto;
	private byte[] lebenslauf;

	private ObjectProperty<Abteilung> abteilung = new SimpleObjectProperty<>();

	private List<Anlage> anlagen;

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

	public StringProperty vornameProperty() {
		return this.vorname;
	}

	public String getVorname() {
		return this.vornameProperty().get();
	}

	public void setVorname(final String vorname) {
		this.vornameProperty().set(vorname);
	}

	public StringProperty nachnameProperty() {
		return this.nachname;
	}

	public String getNachname() {
		return this.nachnameProperty().get();
	}

	public void setNachname(final String nachname) {
		this.nachnameProperty().set(nachname);
	}

	public StringProperty persnrProperty() {
		return this.persnr;
	}

	public String getPersnr() {
		return this.persnrProperty().get();
	}

	public void setPersnr(final String persnr) {
		this.persnrProperty().set(persnr);
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public byte[] getLebenslauf() {
		return lebenslauf;
	}

	public void setLebenslauf(byte[] lebenslauf) {
		this.lebenslauf = lebenslauf;
	}

	public ObjectProperty<Abteilung> abteilungProperty() {
		return this.abteilung;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "abteilung_id")
	public Abteilung getAbteilung() {
		return this.abteilungProperty().get();
	}

	public void setAbteilung(final Abteilung abteilung) {
		this.abteilungProperty().set(abteilung);
	}

	// @ManyToMany(fetch = FetchType.EAGER, mappedBy = "mitarbeiter", cascade =
	// CascadeType.MERGE)
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinTable(name = "anlage_mitarbeiter", joinColumns = {
			@JoinColumn(name = "mitarbeiter_id") }, inverseJoinColumns = { @JoinColumn(name = "anlage_id") })
	public List<Anlage> getAnlagen() {
		return anlagen;
	}

	public void setAnlagen(List<Anlage> anlagen) {
		this.anlagen = anlagen;
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

	public StringProperty telephoneProperty() {
		return this.telephone;
	}

	public String getTelephone() {
		return this.telephoneProperty().get();
	}

	public void setTelephone(final String telephone) {
		this.telephoneProperty().set(telephone);
	}

	public StringProperty taetigkeitProperty() {
		return this.taetigkeit;
	}

	public String getTaetigkeit() {
		return this.taetigkeitProperty().get();
	}

	public void setTaetigkeit(final String taetigkeit) {
		this.taetigkeitProperty().set(taetigkeit);
	}

}
