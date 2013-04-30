package cz.cvut.fel.reposapi.assembla.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpaceTool {
	enum Type {
		MILESTONES(9),
		STAND_UP(10),
		SOURCE_SVN(12),
		TICKETS(13),
		TIME(14),
		SOURCE_HG(17),
		FILES(18),
		EXTERNAL_SUBVERSION(20),
		MESSAGES(21),
		GITHUB(22),
		WIKI(23),
		SOURCE_P4(24),
		TWITTER(111),
		WEBHOOK(112),
		SOURCE_GIT(128),
		FTP(116),
		CUSTOM_TAB(117),
		SUPPORT(300);

		private final int id;

		private Type(int id) {
			this.id = id;
		}

		public int getToolId() {
			return id;
		}

		public static Type parseType(int id) {
			for (Type type : values()) {
				if (type.id == id) {
					return type;
				}
			}
			return null;
		}
	}

	private String id;
	private String space_id;
	private int tool_id;
	private boolean active;
	private String url;
	private String name;

	public String getId() {
		return id;
	}

	public String getSpaceId() {
		return space_id;
	}

	public int getToolId() {
		return tool_id;
	}

	public Type getToolType() {
		return Type.parseType(tool_id);
	}

	public boolean isActive() {
		return active;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}
}
