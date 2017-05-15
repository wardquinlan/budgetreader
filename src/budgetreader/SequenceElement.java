package budgetreader;

import java.io.IOException;

public class SequenceElement implements Retrievable {
	private long seq;
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_SEQUENCE_ELEMENT) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		seq = ar.readLONG();
	}

	@Override
	public String toString() {
		return "sequenceelement: seq=" + seq;
	}
}
