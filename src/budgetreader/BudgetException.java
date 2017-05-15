package budgetreader;

public class BudgetException extends Exception {
	public BudgetException(String msg) {
		super(msg);
	}
	
	public BudgetException(Exception e) {
		super(e);
	}
}
