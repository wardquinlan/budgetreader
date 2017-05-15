package budgetreader;

import java.io.OutputStream;
import java.util.Properties;

public interface DocumentRenderer {
	public void render(Document doc, Properties props, OutputStream out);
}
