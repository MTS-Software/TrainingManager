package com.training.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "schulung")
public class Schulung {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Date> begin = new SimpleObjectProperty<>();
	private ObjectProperty<Date> end = new SimpleObjectProperty<>();

	private ObjectProperty<Mitarbeiter> mitarbeiter = new SimpleObjectProperty<>();
	private ObjectProperty<Level> level = new SimpleObjectProperty<>();
	private ObjectProperty<Status> status = new SimpleObjectProperty<>();
	private ObjectProperty<Produkt> produkt = new SimpleObjectProperty<>();

	private byte[] dokument;

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

	public ObjectProperty<Date> beginProperty() {
		return this.begin;
	}

	public Date getBegin() {
		return this.beginProperty().get();
	}

	public void setBegin(final Date begin) {
		this.beginProperty().set(begin);
	}

	public ObjectProperty<Date> endProperty() {
		return this.end;
	}

	public Date getEnd() {
		return this.endProperty().get();
	}

	public void setEnd(final Date end) {
		this.endProperty().set(end);
	}

	public byte[] getDokument() {
		return dokument;
	}

	public void setDokument(byte[] dokument) {
		this.dokument = dokument;
	}

	public ObjectProperty<Mitarbeiter> mitarbeiterProperty() {
		return this.mitarbeiter;
	}

	@ManyToOne
	@JoinColumn(name = "mitarbeiter_id")
	public Mitarbeiter getMitarbeiter() {
		return this.mitarbeiterProperty().get();
	}

	public void setMitarbeiter(final Mitarbeiter mitarbeiter) {
		this.mitarbeiterProperty().set(mitarbeiter);
	}

	

	public ObjectProperty<Level> levelProperty() {
		return this.level;
	}

	@ManyToOne
	@JoinColumn(name = "level_id")
	public Level getLevel() {
		return this.levelProperty().get();
	}

	public void setLevel(final Level level) {
		this.levelProperty().set(level);
	}

	public ObjectProperty<Status> statusProperty() {
		return this.status;
	}

	@ManyToOne
	@JoinColumn(name = "status_id")
	public Status getStatus() {
		return this.statusProperty().get();
	}

	public void setStatus(final Status status) {
		this.statusProperty().set(status);
	}

	public ObjectProperty<Produkt> produktProperty() {
		return this.produkt;
	}

	@ManyToOne
	@JoinColumn(name = "produkt_id")
	public Produkt getProdukt() {
		return this.produktProperty().get();
	}

	public void setProdukt(final Produkt produkt) {
		this.produktProperty().set(produkt);
	}

}
