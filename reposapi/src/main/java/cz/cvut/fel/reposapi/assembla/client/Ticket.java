package cz.cvut.fel.reposapi.assembla.client;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {
	public enum State {
		OPEN, CLOSED;

		public static State parse(int value) {
			return (value == 0) ? CLOSED : OPEN;
		}
	}

	private String id;
	private int number;
	private String summary;
	private String description;
	private int state;
	private int priority;

	@JsonDeserialize(using = AssemblaDateDeserializer.class)
	private Date updated_at;

	public String getId() {
		return id;
	}

	public int getNumber() {
		return number;
	}

	public String getSummary() {
		return summary;
	}

	public String getDescription() {
		return description;
	}

	public int getState() {
		return state;
	}

	public State getTicketState() {
		return State.parse(state);
	}

	public int getPriority() {
		return priority;
	}

	public Date getUpdatedAt() {
		return updated_at;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", number=" + number + ", summary=" + summary + ", description=" + description + ", state=" + state + ", priority="
				+ priority + ", updated_at=" + updated_at + "]";
	}

}
