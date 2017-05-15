package budgetreader;

import java.io.IOException;
import java.util.ArrayList;

public class SequenceList implements Retrievable {
	private ArrayList<SequenceElement> list;
	
	public SequenceList() {
		list = new ArrayList<SequenceElement>();
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_SEQUENCE_LIST) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		while (true) {
			ot = ar.readHeader();
			if (ot == Constants.OT_END_MARKER) {
				break;
			}
			SequenceElement sequenceElement = new SequenceElement();
			sequenceElement.retrieve(ar, ot);
			list.add(sequenceElement);
		}
	}

	@Override
	public String toString() {
		return "sequencelist";
	}

	public ArrayList<SequenceElement> getList() {
		return list;
	}
}
