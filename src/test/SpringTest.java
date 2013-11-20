import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.EmsDao;


public class SpringTest {
	public static void main(String[] args) {
		EmsDao dao = DaoFactory.getEmsDao();
		dao.selectEms();
	}
	
}
