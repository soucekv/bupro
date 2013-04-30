package cz.cvut.fel.reposapi.assembla;

import java.util.Date;

import cz.cvut.fel.reposapi.Issue;
import cz.cvut.fel.reposapi.IssueState;
import cz.cvut.fel.reposapi.assembla.client.Space;
import cz.cvut.fel.reposapi.assembla.client.Ticket;

public class AssemblaIssue implements Issue {

	private final Space space;
	private final Ticket ticket;

	public AssemblaIssue(Space space, Ticket ticket) {
		this.space = space;
		this.ticket = ticket;
	}

	public String getExternalUrl() {
		return "https://www.assembla.com/spaces/" + space.getName() + "/tickets/" + ticket.getNumber();
	}

	public String getTitle() {
		return ticket.getSummary();
	}

	public IssueState getState() {
		Ticket.State state = ticket.getTicketState();
		return (state == Ticket.State.OPEN) ? IssueState.OPEN : IssueState.CLOSED;
	}

	public Date getUpdatedAt() {
		return ticket.getUpdatedAt();
	}

	@Override
	public String toString() {
		return getClass().getName() + " [getExternalUrl()=" + getExternalUrl() + ", getTitle()=" + getTitle() + ", getState()=" + getState() + ", getUpdatedAt()="
				+ getUpdatedAt() + "]";
	}

}
