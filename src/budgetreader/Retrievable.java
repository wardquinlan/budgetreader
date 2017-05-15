package budgetreader;

import java.io.IOException;

public interface Retrievable {
	public void retrieve(Archive ar, int ot) throws IOException, BudgetException;
}
