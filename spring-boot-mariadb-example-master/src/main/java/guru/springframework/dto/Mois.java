package guru.springframework.dto;

public enum Mois {
	January("January"), February("February"), March("March"), April("April"), May("May"), June("June"),
	July("July"), August("August"), September("September"), October("October"), November("November"),
	December("December");

	private final String name;

	private Mois(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

}
