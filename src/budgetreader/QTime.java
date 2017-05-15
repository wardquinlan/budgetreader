package budgetreader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QTime implements Retrievable {
	private static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private Date date;
	
	public QTime() {
		date = new Date();
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_TIME) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		date.setTime(ar.readLONG() * 1000L);
		int defaultformat = ar.readWORD(); // ignore
		int formatMax = ar.readWORD(); // ignore
		ot = ar.readHeader();
		QString format = new QString();
		format.retrieve(ar, ot); // ignore
	}
	
	public String toString() {
		return df.format(date);
	}
}
