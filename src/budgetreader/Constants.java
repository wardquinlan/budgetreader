package budgetreader;

public class Constants {
	public static final int OT_STRING  = 0x0800;
	public static final int OT_TIME    = 0x0801;
	public static final int OT_VERSION = 0x0802;

	public static final int OT_END_MARKER       = 0x1000;
	public static final int OT_DOCUMENT         = 0x1001;
	public static final int OT_ACCOUNT_LIST     = 0x1002;
	public static final int OT_ACCOUNT          = 0x1003;
	public static final int OT_TXB_LIST         = 0x1004;
	public static final int OT_TXB              = 0x1005;
	public static final int OT_TRANSACTION      = 0x1006;
	public static final int OT_MONEY            = 0x1007;
	public static final int OT_SEQUENCE_ELEMENT = 0x1008; 
	public static final int OT_SEQUENCE_LIST    = 0x1009;
		
	public static final int VERSION_MAJOR    = 2;
	public static final int VERSION_MINOR    = 0;
	public static final int VERSION_REVISION = 0;
	
	public static final int SIGNATURE = 0x4971;
}
