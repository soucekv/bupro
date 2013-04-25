package cz.cvut.fel.reposapi;

import java.util.Date;

public interface Issue extends Linkable {
	String getTitle();

	IssueState getState();

	Date getUpdatedAt();
}
