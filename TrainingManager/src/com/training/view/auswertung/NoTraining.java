package com.training.view.auswertung;

import com.training.model.Anlage;
import com.training.model.Hersteller;
import com.training.model.Produkt;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class NoTraining {

	private ObjectProperty<Anlage> anlage = new SimpleObjectProperty<>();
	private ObjectProperty<Hersteller> hersteller = new SimpleObjectProperty<>();
	private ObjectProperty<Produkt> produkt = new SimpleObjectProperty<>();

	public ObjectProperty<Anlage> anlageProperty() {
		return this.anlage;
	}

	public Anlage getAnlage() {
		return this.anlageProperty().get();
	}

	public void setAnlage(final Anlage anlage) {
		this.anlageProperty().set(anlage);
	}

	public ObjectProperty<Hersteller> herstellerProperty() {
		return this.hersteller;
	}

	public Hersteller getHersteller() {
		return this.herstellerProperty().get();
	}

	public void setHersteller(final Hersteller hersteller) {
		this.herstellerProperty().set(hersteller);
	}

	public ObjectProperty<Produkt> produktProperty() {
		return this.produkt;
	}

	public Produkt getProdukt() {
		return this.produktProperty().get();
	}

	public void setProdukt(final Produkt produkt) {
		this.produktProperty().set(produkt);
	}

}