package budgetreader;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class DocumentFactory {
	private static Log log = LogFactory.getLog(DocumentFactory.class);

	public Document create(String file) throws BudgetException {
		Document doc = new Document();
		try {
			Archive ar = new Archive(new DataInputStream(new FileInputStream(file)));
			int ot = ar.readHeader();
			doc.retrieve(ar, ot);
			ar.close();
		} catch(IOException e) {
			throw new BudgetException(e);
		} catch(BudgetException e) {
			log.error("budget exception", e);
		}
		return doc;
	}
}
