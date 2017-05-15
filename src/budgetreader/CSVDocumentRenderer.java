package budgetreader;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class CSVDocumentRenderer implements DocumentRenderer {
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
					StringBuffer sb = new StringBuffer();
					sb.append(txb.getSeq());
					sb.append(props.get("separator-char"));
					sb.append(acct.getName());
					sb.append(props.get("separator-char"));
					sb.append(txb.getTx().getDate());
					sb.append(props.get("separator-char"));
					sb.append(txb.getTx().getAmount());
					sb.append(props.get("separator-char"));
					sb.append(txb.getBalance());
					ps.println(sb.toString());
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
					StringBuffer sb = new StringBuffer();
					sb.append(txb.getSeq());
					sb.append(props.get("separator-char"));
					sb.append(acct.getName());
					sb.append(props.get("separator-char"));
					sb.append(txb.getTx().getDate());
					sb.append(props.get("separator-char"));
					sb.append(txb.getTx().getAmount());
					sb.append(props.get("separator-char"));
					sb.append(txb.getBalance());
					sb.append(props.get("separator-char"));
					sb.append(txb.getTx().getDescription().toString());
					ps.println(sb.toString());
				}
			}
		}
	}
}
