import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class HtmlMaker {

	private static final String CSV = "files/pbge-methods-list.csv";
	private static final String OUTPUT = "files/pbge-methods.txt";

	private PrintWriter pw;
	private ArrayList<String> methodlists;
	private ArrayList< String[] > lines;

	public HtmlMaker(String[] args) {

		try {
			pw = new PrintWriter(new File(OUTPUT));
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println( OUTPUT + ": file not found" );
			return;
		}

		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream( CSV );
			reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println( CSV +": file not found" );
		}

		if (reader!=null) {

			methodlists = new ArrayList<String>();
			lines = new ArrayList<String[]>();

			try {
				String nextLine = reader.readLine();
				while (nextLine!=null) {
					String[] line = nextLine.split("§");
					lines.add( line );
					nextLine = reader.readLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			printLines();

		}


		pw.close();

	}

	private void printLines() {

		ArrayList< String > methodalphabetic = new ArrayList<String>();
		Iterator< String[] > itl = lines.iterator();
		while( itl.hasNext() ) {
			String[] line = itl.next();
			if ( line.length == 0 )
				continue;
			if ( line.length == 1 ) {
				methodlists.add("<div class=\"method_section\">"+line[0]+"</div>");
			} else {
				String anchor = line[2].substring(0, line[2].length() - 2 );
				String ml = "<div class=\"method_anchor\"><a href=\"#"+anchor+"\">"+line[2]+"</a></div>";
				if ( !methodlists.contains( ml ) ) {
					methodlists.add( ml );
					methodalphabetic.add( line[2] );
				}
			}
		}
		String[] alpham = new String[ methodalphabetic.size() ];
		for ( int i = 0; i < alpham.length; i++ )
			alpham[ i ] = methodalphabetic.get( i );

		Arrays.sort( alpham );

		// printing method list
		Iterator< String > itml = methodlists.iterator();
		pw.println( "<div class=\"methods_summary\">" );
		pw.println( "<a name=\"methods_summary\"></a>" );
		while( itml.hasNext() ) {
			pw.println( itml.next() );
			pw.flush();
		}
		pw.println( "</div>" );
		pw.println( "<div class=\"methods_list\">" );
		// printing list


		for ( int i = 0; i < alpham.length; i++ ) {

			System.out.println( i + " / " + alpham[ i ] );

			boolean started = false;

			for ( int j = 0; j < lines.size(); j++ ) {

				String[] line = lines.get( j );
				String[] nextline = null;

				if ( j < lines.size() -1 ) {
					nextline = lines.get( j + 1 );
					if ( nextline.length < 2 )
						nextline = null;
				}

				if ( line.length < 2 )
					continue;

				if ( line[ 2 ].equals( alpham[ i ] ) ) {

					if ( !started ) {
						String anchor = line[2].substring(0, line[2].length() - 2 );
						pw.println( "<div class=\"method\">");
						pw.println( "<a name=\""+anchor+"\"></a>");
						pw.println( "<div class=\"method_"+line[1]+"\">"+line[1]+"</div>");
						pw.println( "<div class=\"method_name\">"+line[2]+"</div>");
						started = true;
					}

					pw.println( "<div class=\"method_arguments\">");

					// parsing arguments
					String[] argus = line[3].split(";");

					if ( argus.length == 0 )
						pw.println( "-" );
					else if ( ( argus.length == 1 ) && ( argus.length == 1 && argus[ 0 ].indexOf( '[' ) == -1 ) )
						pw.println( argus[ 0 ] );

					else {

						ArrayList< String > headings = new ArrayList<String>();
						ArrayList< ArrayList< String > > rows = new ArrayList<ArrayList<String>>();
						for ( int a = 0; a < argus.length; a++ ) {
							String ar = argus[ a ];
							String conf = ar.substring( ar.indexOf( '[' )+1 , ar.indexOf( ']' ) );
							String name = ar.substring( ar.indexOf( ']' )+1 );
							ArrayList< String > r = new ArrayList< String >();
							r.add( name.trim() );
							if ( headings.size() < 1 )
								headings.add("name");
							String type = conf.substring( 0, conf.indexOf( ':' ) );
							if ( type.trim().equals( "num" ) )
								r.add( "number" );
							else
								r.add( type.trim() );
							if ( headings.size() < 2 )
								headings.add("type");

							String defv = conf.substring( conf.indexOf( ':' )+1 );
							if ( defv.indexOf( '|' ) > -1 ) {
								r.add( defv.substring( 0, defv.indexOf( '|' ) ) );
								if ( headings.size() < 3 )
									headings.add("default");
								r.add( defv.substring( defv.indexOf( '|' )+1 ) );
								if ( headings.size() < 4 )
									headings.add("range");
							} else {
								r.add( defv );
								if ( headings.size() < 3 )
									headings.add("default");
							}
							rows.add( r );

						}

						pw.println( "<table>");
						pw.println( "<tr class=\"arguments_header\">");
						for ( int h = 0; h < headings.size(); h++ )
							pw.print( "<td>" + headings.get( h ) + "</td>" );
						pw.println( "</tr>");
						for ( int r = 0; r < rows.size(); r++ ) {
							pw.println( "<tr>");
							for ( int c = 0; c < rows.get( r ).size(); c++ ) {
								pw.print( "<td>" + rows.get( r ).get(c) + "</td>" );
							}
							pw.println( "</tr>");
						}
						pw.println( "</table>");


					}

					pw.println( "</div>");
					pw.println( "<div class=\"method_return\">"+line[4]+"</div>");
					pw.println( "<div class=\"method_comments\">"+line[5]+"</div>");
					if ( line.length == 7 ) {
						pw.println( "<div class=\"method_important\">"+line[6]+"</div>");
					}

				} else {

					if ( started )
						break;

				}

				pw.flush();

			}

			if ( started ) {
				pw.println( "<div class=\"backtosummary\"><a href=\"#methods_summary\">back to summary</a></div>");
				pw.println( "</div>" );
				pw.flush();
			}
		}

//		for ( int i = 0; i < lines.size(); i++ ) {
//
//			String[] line = lines.get( i );
//			String[] previousline = null;
//			String[] nextline = null;
//			if ( i < lines.size() -1 ) {
//				nextline = lines.get( i + 1 );
//				if ( nextline.length < 2 )
//					nextline = null;
//			}
//			if ( i > 0 ) {
//				previousline = lines.get( i - 1 );
//				if ( previousline.length < 2 )
//					previousline = null;
//			}
//			if ( line.length == 0 )
//				continue;
//			if ( line.length == 1 ) {
//				pw.println( "<div class=\"section\">" + line[0] + "</div>" );
//			} else {
//				if ( previousline != null && !previousline[ 2 ].equals( line[2] ) ) {
//					String anchor = line[2].substring(0, line[2].length() - 2 );
//					pw.println( "<div class=\"method\">");
//					pw.println( "<a name=\""+anchor+"\"></a>");
//					pw.println( "<div class=\"method_privacy\">"+line[1]+"</div>");
//				}
//				pw.println( "<div class=\"method_name\">"+line[2]+"</div>");
//				pw.println( "<div class=\"method_arguments\">"+line[3]+"</div>");
//				pw.println( "<div class=\"method_return\">"+line[4]+"</div>");
//
//				if ( nextline != null && !nextline[ 2 ].equals( line[2] ) ) {
//					pw.println( "<div class=\"method_comments\">"+line[5]+"</div>");
//				}
//				if ( line.length == 7 ) {
//					pw.println( "<div class=\"method_important\">"+line[6]+"</div>");
//				}
//				if ( nextline != null && !nextline[ 2 ].equals( line[2] ) ) {
//					pw.println( "<div class=\"backtosummary\"><a href=\"#methods_summary\">back to summary</a></div>");
//					pw.println( "</div>" );
//				}
//			}
//			pw.flush();
//		}

		pw.println( "</div>" );

	}

	public static final void main(String[] args) {
		HtmlMaker hm = new HtmlMaker(args);
	}

}
