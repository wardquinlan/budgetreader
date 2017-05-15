package budgetreader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BudgetReader {
	private static Log log = LogFactory.getLog(BudgetReader.class);
	
	public static void main(String[] args) {
		Properties props = new Properties();
		CommandLine line = null;
		Options options = new Options();
		options.addOption(new Option("t", "tsv", false, "generate tab-separated value output"));
		options.addOption(new Option("c", "csv", false, "generate comma-separated value output"));
		options.addOption(new Option("a", "account", true, "limit output to account"));
		options.addOption(new Option("s", "summary", false, "generate account summary statement"));
		options.addOption(new Option("h", "help", false, "show help"));
		CommandLineParser parser = new BasicParser();
		HelpFormatter formatter = new HelpFormatter();
		System.out.println("Budget Reader v1.00");
		try {
			line = parser.parse(options, args);
			args = line.getArgs();
		} catch(ParseException e) {
			formatter.printHelp( "br [OPTIONS] BUDGET.BDG", options );
			System.exit(1);
		}
		if (line.hasOption("help")) {
			formatter.printHelp( "br [OPTIONS] BUDGET.BDG", options );
			System.exit(0);
		}
		if (args.length != 1) {
			formatter.printHelp( "br [OPTIONS] BUDGET.BDG", options );
			System.exit(1);
		}
		if (line.hasOption("tsv") && line.hasOption("csv")) {
			formatter.printHelp( "br [OPTIONS] BUDGET.BDG", options );
			System.exit(1);
		}
		if (line.hasOption("account") && line.hasOption("summary")) {
			formatter.printHelp( "br [OPTIONS] BUDGET.BDG", options );
			System.exit(1);
		}
		PrintStream ps = null;
		ApplicationContext ctx = new ClassPathXmlApplicationContext("app-config.xml");
		DocumentFactory factory = ctx.getBean(DocumentFactory.class);
		DocumentRenderer renderer;
		try {
			if (line.hasOption("tsv")) {
				renderer = ctx.getBean("csvDocumentRenderer", DocumentRenderer.class);
				ps = new PrintStream(new FileOutputStream(args[0] + ".csv"));
				props.put("separator-char", "\t");
			} else if (line.hasOption("csv")) {
				renderer = ctx.getBean("csvDocumentRenderer", DocumentRenderer.class);
				ps = new PrintStream(new FileOutputStream(args[0] + ".csv"));
				props.put("separator-char", ",");
			} else {
				renderer = ctx.getBean("simpleDocumentRenderer", DocumentRenderer.class);
				ps = System.out;
			}
			Document doc = factory.create(args[0]);
			if (line.getOptionValue("account") != null) {
				props.put("account", line.getOptionValue("account"));
			}
			if (line.hasOption("summary")) {
				props.put("summary", "true");
			}
			renderer.render(doc, props, ps);
		} catch(BudgetException e) {
			log.error("error during processing of " + args[0], e);
		} catch(FileNotFoundException e) {
			log.error("error during processing of " + args[0], e);
		} finally {
			if (ps != System.out) {
				ps.close();
			}
		}
	}
}
