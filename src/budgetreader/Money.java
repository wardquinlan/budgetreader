package budgetreader;

import java.io.IOException;
import java.text.DecimalFormat;

public class Money implements Retrievable {
	private static DecimalFormat df = new DecimalFormat("########0.00");
	private Long cents;
	
	public Money() {
		cents = new Long(0L);
	}
	
	public Money(Long cents) {
		this.cents = cents;
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_MONEY) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		cents = new Long(ar.readLONG());
	}
	
	public String toString() {
		return df.format(cents.doubleValue() / (double) 100);
	}
	
	public Long getCents() {
		return cents;
	}
}
