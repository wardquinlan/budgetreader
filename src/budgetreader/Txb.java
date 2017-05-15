package budgetreader;

import java.io.IOException;

public class Txb implements Retrievable, Comparable {
	private long seq;
	private Transaction tx;
	private Money balance;
	
	public Txb() {
		tx = new Transaction();
		balance = new Money();
	}
	
	public Txb(long seq, Transaction tx) {
		this.seq = seq;
		this.tx = tx;
	}
	
	public int compareTo(Object o) {
		Txb txb = (Txb) o;
		if (this.seq < txb.seq) {
			return -1;
		} else if (this.seq > txb.seq) {
			return +1;
		} else {
			return 0;
		}
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_TXB) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		ot = ar.readHeader();
		tx.retrieve(ar, ot);
		ot = ar.readHeader();
		balance.retrieve(ar, ot);
		seq = ar.readLONG();
	}

	@Override
	public String toString() {
		return "txb: seq= " + seq + "; balance=" + balance;
	}

	public Transaction getTx() {
		return tx;
	}

	public long getSeq() {
		return seq;
	}

	public Money getBalance() {
		return balance;
	}
	
	public void setBalance(Money balance) {
		this.balance = balance;
	}
}
