package budgetreader;

import java.io.IOException;

public class QString implements Retrievable {
	public String value;
	
	public QString() {
		value = new String();
	}
	
	public QString(String value) {
		this.value = value;
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_STRING) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		int b = ar.readBYTE();
		if (b == 0) {
			return;
		}
		int cb = ar.readWORD();
		value = ar.readString(cb);
	}
	
	public String toString() {
		return value;
	}
}
