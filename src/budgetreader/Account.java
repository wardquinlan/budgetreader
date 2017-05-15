package budgetreader;

import java.io.IOException;

public class Account implements Retrievable {
	private QString name;
	private TxbList list;
	
	public Account() {
		name = new QString();
		list = new TxbList();
	}
	
	public Account(String name) {
		this.name = new QString(name);
		list = new TxbList();
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_ACCOUNT) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		ot = ar.readHeader();
		name.retrieve(ar, ot);
		ot = ar.readHeader();
		list.retrieve(ar, ot);
	}
	
	@Override
	public String toString() {
		return "account: name=" + name;
	}

	public String getName() {
		return name.toString();
	}
	
	public TxbList getList() {
		return list;
	}
}
