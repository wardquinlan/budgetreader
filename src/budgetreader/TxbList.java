package budgetreader;

import java.io.IOException;
import java.util.ArrayList;

public class TxbList implements Retrievable {
	private ArrayList<Txb> list;
	
	public TxbList() {
		list = new ArrayList<Txb>();
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_TXB_LIST) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		while (true) {
			ot = ar.readHeader();
			if (ot == Constants.OT_END_MARKER) {
				break;
			}
			Txb txb = new Txb();
			txb.retrieve(ar, ot);
			list.add(txb);
		}
	}

	@Override
	public String toString() {
		return "txblist";
	}

	public ArrayList<Txb> getList() {
		return list;
	}
}
