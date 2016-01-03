/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.adapter.example.model;

/**
 * An utility class, which provides the sample data, which is used by the
 * example app.
 *
 * @author Michael Rapp
 */
public final class SampleData {

	/**
	 * Creates a new utility class, which provides the sample data, which is
	 * used by the example app.
	 */
	private SampleData() {

	}

    /**
     * The country, which represents the united states.
     */
    public static final Country COUNTRY_US = new Country("USA");

    /**
     * The contacts, which belong to the united states.
     */
    public static final Contact[] CONTACTS_US = {

            new Contact("James",		"Butt", 		"6649 N Blue Gum St", 		"New Orleans"),
            new Contact("Josephine", 	"Darakjy", 		"4 B Blue Ridge Blvd", 		"Brighton"),
            new Contact("Art", 			"Venere", 		"8 W Cerritos Ave #54", 	"Bridgeport"),
            new Contact("Lena", 		"Paprocki",		"639 Main St", 				"Anchorage"),
            new Contact("Donette", 		"Foller", 		"34 Center St", 			"Hamilton"),
            new Contact("Simona", 		"Morasca", 		"3 Mcauley Dr", 			"Ashland"),
            new Contact("Mitsue", 		"Tollner", 		"7 Eads St", 				"Chicago"),
            new Contact("Leota", 		"Dilliard",		"7 W Jackson Blvd", 		"San Jose"),
            new Contact("Sage", 		"Wieser", 		"5 Boston Ave #88", 		"Sioux Falls"),
            new Contact("Kris", 		"Marrier", 		"228 Runamuck Pl #2808", 	"Baltimore"),
            new Contact("Minna", 		"Amigon", 		"2371 Jerrold Ave", 		"Kulpsville"),
            new Contact("Abel", 		"Maclead", 		"37275 St  Rt 17m M", 		"Middle Island"),
            new Contact("Kiley", 		"Caldarera",	"25 E 75th St #69", 		"Los Angeles"),
            new Contact("Graciela", 	"Ruta",			"98 Connecticut Ave Nw",	"Chagrin Falls"),
            new Contact("Cammy", 		"Albares", 		"56 E Morehead St", 		"Laredo"),
            new Contact("Mattie", 		"Poquette", 	"73 State Road 434 E", 		"Phonix"),
            new Contact("Meaghan", 		"Garufi", 		"69734 E Carrillo St", 		"Mc Minnville"),
            new Contact("Gladys", 		"Rim", 			"322 New Horizon Blvd", 	"Milwaukee"),
            new Contact("Yuki", 		"Whobrey", 		"1 State Route 27", 		"Taylor"),
            new Contact("Fletcher", 	"Flosi", 		"394 Manchester Blvd", 		"Rockford"),

    };

    /**
     * The country, which represents the united kingdom.
     */
    public static final Country COUNTRY_UK = new Country("United Kingdom");

    /**
     * The contacts, which belong to the united kingdom.
     */
    public static final Contact[] CONTACTS_UK = {

            new Contact("Aleshia",		"Tomkiewicz", 	"14 Taylor St",				"St. Stephens Ward"),
            new Contact("Evan", 		"Zigomalas", 	"5 Minney St",				"Abbey Ward"),
            new Contact("France", 		"Andrade", 		"8 Moor Place", 			"East Southbourne and Tuckton W"),
            new Contact("Ulysses", 		"Mcwalters",	"505 Exeter Rd",			"Hawerby cum Beesby"),
            new Contact("Tyisha", 		"Veness", 		"5396 Forth Street",		"Greets Green and Lyng Ward"),
            new Contact("Eric", 		"Rampy", 		"9472 Cowl St #70",			"Desborough"),
            new Contact("Marg", 		"Grasmick", 	"20 Gloucester Pl #96",		"Bargate Ward"),
            new Contact("Laquita", 		"Hisaw",		"929 Augustine St",			"Chirton Ward"),
            new Contact("Lura", 		"Manzella", 	"45 Bradfield St #166",		"Staple Hill Ward"),
            new Contact("Yuette", 		"Klapec", 		"620 Northampton St", 		"Parwich"),
            new Contact("Fernanda", 	"Writer", 		"5 Hygeia St",				"Wilmington"),
            new Contact("Charlesetta", 	"Erm", 			"2150 Morley St",			"Loundsley Green Ward"),
            new Contact("Corrine", 		"Jaret",		"4 Forrest St",				"Dee Ward"),
            new Contact("Niesha", 		"Bruch",		"89 Noon St",				"Broxburn, Uphall and Winchburg"),
            new Contact("Rueben", 		"Gastellum", 	"99 Guthrie St", 			"Weston-Super-Mare"),
            new Contact("Michell", 		"Throssell", 	"7 Richmond St", 			"Carbrooke"),
            new Contact("Edgar", 		"Kanne", 		"9165 Primrose St", 		"New Milton"),
            new Contact("Dewitt", 		"Julio", 		"9 Pengwern St", 			"Parkham"),
            new Contact("Charisse", 	"Spinello", 	"4410 Tarlton St", 			"Darnall Ward"),
            new Contact("Mee", 			"Lapinski", 	"6949 Bourne St", 			"Marldon"),

    };

    /**
     * The country, which represents Canada.
     */
    public static final Country COUNTRY_CA = new Country("Canada");

    /**
     * The contacts, which belong to Canada.
     */
    public static final Contact[] CONTACTS_CA = {

            new Contact("Francoise",	"Airhart",		"2335 Canton Hwy #6",		"Windsor"),
            new Contact("Kendra",		"Loud", 		"6 Arch St #9757",			"Alcida"),
            new Contact("Lourdes",		"Bauswell", 	"9547 Belmont Rd #21",		"Belleville"),
            new Contact("Hannah",		"Edmison", 		"73 Pittsford Victor Rd",	"Vancouver"),
            new Contact("Tom",			"Loeza", 		"447 Commercial St Se",		"Lile-Perror"),
            new Contact("Queenie",		"Kramarczyk",	"47 Garfield Ave",			"Swift Current"),
            new Contact("Hui",			"Portaro", 		"3 Mill Rd",				"Baker Brook"),
            new Contact("Josefa",		"Opitz", 		"136 W Grand Ave #3",		"Delhi"),
            new Contact("Lea",			"Steinhaus", 	"80 Maplewood Dr #34",		"Bradford"),
            new Contact("Paola",		"Vielma", 		"58 Hancock St",			"Aurora"),
            new Contact("Hortencia",	"Bresser", 		"808 Calle De Industrias",	"New Waterford"),
            new Contact("Leanna",		"Tijerina", 	"2859 Dorsett Rd",			"North York"),
            new Contact("Danilo",		"Pride", 		"6857 Wall St",				"Red Deer"),
            new Contact("Huey",			"Marcille", 	"169 Journal Sq",			"Edmonton"),
            new Contact("Apolonia",		"Warne", 		"3 E 31st St #77",			"Fredericton"),
            new Contact("Chandra",		"Lagos", 		"7 N Dean St",				"Etobicoke"),
            new Contact("Crissy",		"Pacholec", 	"85 S State St",			"Barrie"),
            new Contact("Gianna",		"Branin", 		"100 Main St",				"Calgary"),
            new Contact("Valentin",		"Billa", 		"6185 Bohn St #72",			"Pangman"),
            new Contact("Ilona",		"Dudash", 		"2 Sutton Pl S #5727",		"Rouyn-Noranda"),

    };

    /**
     * The country, which represents Australia.
     */
    public static final Country COUNTRY_AU = new Country("Australia");

    /**
     * The contacts, which belong to Australia.
     */
    public static final Contact[] CONTACTS_AU = {

            new Contact("Rebbecca",		"Didio", 		"171 E 24th St",			"Leith"),
            new Contact("Stevie",		"Hallo", 		"22222 Acoma St",			"Proston"),
            new Contact("Mariko",		"Stayer", 		"534 Schoenborn St #51",	"Hamel"),
            new Contact("Gerardo",		"Woodka", 		"69206 Jackson Ave",		"Talmalmo"),
            new Contact("Mayra",		"Bena", 		"808 Glen Cove Ave",		"Lane Cove"),
            new Contact("Idella",		"Scotland", 	"373 Lafayette St",			"Cartmeticup"),
            new Contact("Sherill",		"Klar", 		"87 Sylvan Ave",			"Nyamup"),
            new Contact("Ena",			"Desjarsiws", 	"60562 Ky Rt 321",			"Bendick Murrell"),
            new Contact("Vince",		"Siena", 		"70 S 18th Pl",				"Purrawunda"),
            new Contact("Theron",		"Jarding", 		"8839 Ventura Blvd",		"Blanchetown"),
            new Contact("Amira",		"Chudej", 		"3684 N Wacker Dr",			"Rockside"),
            new Contact("Marica",		"Tarbor", 		"68828 S 32nd St #6",		"Rosegarland"),
            new Contact("Shawna",		"Albrough", 	"43157 Cypress St",			"Ringwood"),
            new Contact("Paulina",		"Maker", 		"6 S Hanover Ave",			"Maylands"),
            new Contact("Rose",			"Jebb", 		"27916 Tarrytown Rd",		"Wooloowin"),
            new Contact("Reita",		"Tabar", 		"79620 Timber Dr",			"Arthurville"),
            new Contact("Maybelle",		"Bewley", 		"387 Airway Cir #62",		"Mapleton"),
            new Contact("Camellia",		"Pylant", 		"570 W Pine St",			"Tuggerawong"),
            new Contact("Roy",			"Nybo", 		"823 Fishers Ln",			"Red Hill"),
            new Contact("Albert",		"Sonier", 		"4 Brookcrest Dr #7786",	"Inverlaw"),

    };

}