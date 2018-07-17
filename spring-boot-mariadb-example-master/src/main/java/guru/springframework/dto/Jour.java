package guru.springframework.dto;

public enum Jour {
	Monday("Lundi"), Tuesday("Mardi"), Wednesday("Mercredi"), Thursday("Jeudi"), Friday("Vendredi"), Saturday(
			"Samedi"), Sunday("Dimanche");

	private final String name;

	private Jour(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
