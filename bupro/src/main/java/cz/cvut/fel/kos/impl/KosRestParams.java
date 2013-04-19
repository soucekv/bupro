package cz.cvut.fel.kos.impl;

/**
 * Seznam všech obecných URL parametrů používaných v KOSapi. Tyto parametry se
 * zapisují do URL jako query string.
 * 
 */
public class KosRestParams {

	/**
	 * Maximální počet požadovaných záznamů, neboli po kolika záznamech se má
	 * stránkovat (v předchozí verzi maxResults)<br />
	 * Vstup: 1 - 1000<br />
	 * Působnost: Atom Feed
	 */
	public static final String LIMIT = "limit";

	/**
	 * Popis: Index prvního požadovaného záznamu (číslované od nuly) pro
	 * stránkování (v předchozí verzi startIndex)<br />
	 * Vstup: 0 - 21 4748 3647<br />
	 * Působnost: Atom Feed
	 */
	public static final String OFFSET = "offset";

	/**
	 * Popis: Specifikuje řazení záznamů<br />
	 * Vstup: Ordering<br />
	 * Působnost: Atom Feed
	 */
	public static final String ORDERBY = "orderBy";
}
