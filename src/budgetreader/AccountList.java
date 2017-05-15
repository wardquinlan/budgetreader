package budgetreader;

import java.io.IOException;
import java.util.ArrayList;

public class AccountList implements Retrievable {
	private ArrayList<Account> list;
	
	public AccountList() {
		list = new ArrayList<Account>();
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_ACCOUNT_LIST) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		while (true) {
			ot = ar.readHeader();
			if (ot == Constants.OT_END_MARKER) {
				break;
			}
			Account account = new Account();
			account.retrieve(ar, ot);
			list.add(account);
		}
	}
	
	@Override
	public String toString() {
		return "accountlist";
	}

	public ArrayList<Account> getList() {
		return list;
	}
}
