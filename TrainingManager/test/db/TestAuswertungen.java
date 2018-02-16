package db;

import com.training.db.util.DAOException;
import com.training.db.util.HibernateUtil;

public class TestAuswertungen {

	public static void main(String[] args) {
		try {
			new TestAuswertungen();

			HibernateUtil.getSessionFactory().close();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Suche nach Anlagen ohne Schulung für HerstellerTechnologie
	public TestAuswertungen() throws DAOException {

	}

}
