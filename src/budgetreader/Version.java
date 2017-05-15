package budgetreader;

import java.io.IOException;

public class Version implements Retrievable {
	private int major;
	private int minor;
	private int revision;
	
	public Version() {
	}

	public void retrieve(Archive ar, int ot) throws IOException, BudgetException {
		if (ot != Constants.OT_VERSION) {
			throw new BudgetException("unexpected object type: " + Integer.toHexString(ot));
		}
		major = ar.readWORD();
		minor = ar.readWORD();
		revision = ar.readWORD();
	}
	
	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(major);
		sb.append(".");
		sb.append(minor);
		sb.append(".");
		sb.append(revision);
		return sb.toString();
	}
}
