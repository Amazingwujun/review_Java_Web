import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.zip.GZIPOutputStream;

import org.junit.Test;

public class atest {
	@Test
	public void test1() throws Exception {
		StringWriter sw = new StringWriter();
		sw.write("nani");
		sw.write("haha");
		System.out.println(sw.toString());
	}
}
