package budgetreader;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Document implements Retrievable {
	private Version version;
	private AccountList list;
	private SequenceList listSeq;
	
	public Document() {
		version = new Version();
		list = new AccountList();
		listSeq = new SequenceList();
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_DOCUMENT) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		int sig = ar.readWORD();
		if (sig != Constants.SIGNATURE) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		ot = ar.readHeader();
		version.retrieve(ar, ot);
		if (version.getMajor() != Constants.VERSION_MAJOR ||
		    version.getMinor() != Constants.VERSION_MINOR ||
		    version.getRevision() != Constants.VERSION_REVISION) {
			throw new BudgetException("unexpected version: " + version);
		}
		ot = ar.readHeader();
		list.retrieve(ar, ot);
		ot = ar.readHeader();
		listSeq.retrieve(ar, ot);
		buildGeneralAccount();
	}
	
	private void buildGeneralAccount() {
		Account accountGeneral = new Account("General");
		for (Account account: list.getList()) {
			for (Txb txb: account.getList().getList()) {
				Transaction tx = txb.getTx();
				QTime date = tx.getDate();
				Money amount = tx.getAmount();
				QString description = new QString("[" + account.getName() + "]" + tx.getDescription());
				Transaction txGeneral = new Transaction(date, amount, description);
				Txb txbGeneral = new Txb(txb.getSeq(), txGeneral);
				accountGeneral.getList().getList().add(txbGeneral);
			}
		}
		Collections.sort(accountGeneral.getList().getList());
		List<Txb> txbList = accountGeneral.getList().getList();
		for (int i = 0; i < txbList.size(); i++) {
			Txb txb = txbList.get(i);
			Money moneyBalancePrev;
			if (i == 0) {
				moneyBalancePrev = new Money();
			} else {
				moneyBalancePrev = txbList.get(i - 1).getBalance();
			}
			Money balance = new Money(moneyBalancePrev.getCents() + txb.getTx().getAmount().getCents());
			txb.setBalance(balance);
		}
		list.getList().add(0, accountGeneral);
	}
	
	@Override
	public String toString() {
		return "document: version=" + version;
	}

	public AccountList getList() {
		return list;
	}

	public SequenceList getListSeq() {
		return listSeq;
	}
}
