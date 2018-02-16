package com.training.db.dao;

import com.training.db.util.EDAOType;

public class DAOFactory {

	private AbteilungDAO abteilungDAO;
	private StandortDAO standortDAO;
	private MitarbeiterDAO mitarbeiterDAO;
	private SchulungDAO schulungDAO;
	private LevelDAO levelDAO;
	private StatusDAO statusDAO;
	private ProduktDAO produktDAO;
	private AnlageDAO anlageDAO;
	private HerstellerDAO herstellerDAO;

	public DAOFactory(EDAOType eDAOType) {

		if (eDAOType == EDAOType.JDBC) {

			abteilungDAO = new AbteilungJDBCDAO();
			standortDAO = new StandortJDBCDAO();
			mitarbeiterDAO = new MitarbeiterJDBCDAO();
			schulungDAO = new SchulungJDBCDAO();
			levelDAO = new LevelJDBCDAO();
			statusDAO = new StatusJDBCDAO();
			produktDAO = new ProduktJDBCDAO();
			anlageDAO = new AnlageJDBCDAO();
			herstellerDAO = new HerstellerJDBCDAO();

		}

		if (eDAOType == EDAOType.MEMORY) {

		}

	}

	public AbteilungDAO getAbteilungDAO() {
		return abteilungDAO;
	}

	public StandortDAO getStandortDAO() {
		return standortDAO;
	}

	public MitarbeiterDAO getMitarbeiterDAO() {
		return mitarbeiterDAO;
	}

	public SchulungDAO getSchulungDAO() {
		return schulungDAO;
	}

	public LevelDAO getLevelDAO() {
		return levelDAO;
	}

	public StatusDAO getStatusDAO() {
		return statusDAO;
	}

	public ProduktDAO getTechnologieDAO() {
		return produktDAO;
	}

	public AnlageDAO getAnlageDAO() {
		return anlageDAO;
	}

	public HerstellerDAO getHerstellerDAO() {
		return herstellerDAO;
	}

}
