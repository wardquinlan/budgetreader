package budgetreader;

import java.io.IOException;

public class Transaction implements Retrievable {
	private QTime date;
	private Money amount;
	private QString description;
	
	public Transaction() {
		date = new QTime();
		amount = new Money();
		description = new QString();
	}
	
	public Transaction(QTime date, Money amount, QString description) {
		this.date = date;
		this.amount = amount;
		this.description = description;
	}
	
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_TRANSACTION) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		ot = ar.readHeader();
		date.retrieve(ar, ot);
		ot = ar.readHeader();
		amount.retrieve(ar, ot);
		ot = ar.readHeader();
		description.retrieve(ar, ot);
	}

	@Override
	public String toString() {
		return "transaction: date=" + date + "; amount=" + amount + "; description=" + description;
	}

	public QTime getDate() {
		return date;
	}

	public Money getAmount() {
		return amount;
	}

	public QString getDescription() {
		return description;
	}
}
