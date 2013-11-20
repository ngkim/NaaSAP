import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kt.naas.db.CodeDao;
import com.kt.naas.domain.Code;
import com.kt.naas.util.CodeUtils;


public class Main {
	public static void main(String[] args) {
		try {
			Process process;
			String osName = System.getProperty("os.name");
			String[] cmd;
			
			if (osName.toLowerCase().startsWith("window")) {
				cmd = new String[] {"cmd.exe", "/y", "/c", "net stop SNMPTRAP"};
				process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ApplicationContext 	context = new ClassPathXmlApplicationContext("application-context.xml");
		getAllCodes(context);
		System.out.println("==========================");
		System.out.println("DOCU=" + CodeUtils.getCodeValue("GENREID", "DOCU"));
	}
	
	public static void getAllCodes(ApplicationContext context)
	{
		CodeDao dao = (CodeDao)context.getBean("codeDao");
		List<Code> codes = dao.getAllCodes();
		
		System.out.println(codes.size() + " items selected.");
		for (Code code : codes)
		{
			System.out.println(code);
		}
	}
}
