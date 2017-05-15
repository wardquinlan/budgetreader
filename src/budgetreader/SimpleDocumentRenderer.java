package budgetreader;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class SimpleDocumentRenderer implements DocumentRenderer {
	@Override
	public void render(Document doc, Properties props, OutputStream out) {
		String account;
		if ("true".equals(props.get("summary"))) {
			PrintStream ps = new PrintStream(out);
			ArrayList<Account> list = doc.getList().getList();
			for (Account acct: list) {
				int n = acct.getList().getList().size();
				if (n > 0) {
					Txb txb = acct.getList().getList().get(n - 1);
					ps.printf("%8s %-20s%12s%12s\n", txb.getSeq(), acct.getName(), txb.getTx().getDate(), txb.getBalance());
				}
			}
			return;
		}
		if (props.get("account") == null) {
			account = "General";
		} else {
			account = props.get("account").toString();
		}
		PrintStream ps = new PrintStream(out);
		ArrayList<Account> list = doc.getList().getList();
		for (Account acct: list) {
			if (account.equals(acct.getName())) {
				for (Txb txb: acct.getList().getList()) {
					ps.printf("%8s %-20s%12s%12s%12s %s\n", txb.getSeq(), acct.getName(), txb.getTx().getDate(), txb.getTx().getAmount(), txb.getBalance(), txb.getTx().getDescription().toString());
				}
			}
		}
	}
}
