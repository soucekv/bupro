package cz.cvut.fel.reposapi.assembla.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import cz.cvut.fel.reposapi.assembla.client.Ticket.State;

/**
 * Low level Assembla client. API specific purely to Assembla RESTful API.
 * 
 * @author Viktor Soucek
 * 
 */
public class AssemblaClient {
	private static final String ASSEMBLA_API_URL = "https://api.assembla.com/v1/";
	private static final String TYPE_JSON = ".json";
	private static final String CLIENT_TOKEN_REQEST = "https://api.assembla.com/token?grant_type=client_credentials";

	private final RestTemplate restTemplate = new RestTemplate();

	private static HttpHeaders createAuthorizationHeaders(Identity identity) {
		HttpHeaders httpHeaders = new HttpHeaders();
		identity.authorize(httpHeaders);
		return httpHeaders;
	}

	private <T> T get(String url, Identity identity, Class<T> type) {
		HttpHeaders httpHeaders = createAuthorizationHeaders(identity);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<T>(httpHeaders), type);
		return response.getBody();
	}

	private <T> T get(String url, Identity identity, Class<T> type, Map<String, ?> uriVariables) {
		HttpHeaders httpHeaders = createAuthorizationHeaders(identity);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<T>(httpHeaders), type, uriVariables);
		return response.getBody();
	}

	/**
	 * Using Client credentials authentication (see Assembla API documentation)
	 * obtains authentication {@link Token}
	 * 
	 * @param clientId
	 * @param secret
	 * @return new authentication token instance
	 */
	public Token getClientToken(String clientId, String secret) {
		HttpHeaders headers = BasicAuth.createAuthHeaders(clientId, secret);
		ResponseEntity<Token> response = restTemplate.exchange(CLIENT_TOKEN_REQEST, HttpMethod.POST, new HttpEntity<Token>(headers), Token.class);
		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException("HTTP response " + response.getStatusCode());
		}
		Token token = response.getBody();
		token.initExpiration();
		return token;
	}

	public List<Space> getSpaces(Identity identity) {
		String url = ASSEMBLA_API_URL + "spaces" + TYPE_JSON;
		SpaceList spaceList = get(url, identity, SpaceList.class);
		return spaceList;
	}

	public Space getSpace(Identity identity, String id) {
		String url = ASSEMBLA_API_URL + "spaces/" + id + TYPE_JSON;
		Space space = get(url, identity, Space.class);
		return space;
	}

	public List<SpaceTool> getSpaceTools(Identity identity, Space space) {
		String url = ASSEMBLA_API_URL + "spaces/" + space.getId() + "/space_tools" + TYPE_JSON;
		SpaceToolList spaceToolList = get(url, identity, SpaceToolList.class);
		return spaceToolList;
	}

	public SpaceTool getSpaceRepositoryTool(Identity identity, Space space) {
		String url = ASSEMBLA_API_URL + "spaces/" + space.getId() + "/space_tools" + TYPE_JSON;
		SpaceTool spaceTool = get(url, identity, SpaceTool.class);
		return spaceTool;
	}

	public List<Ticket> getSpaceTickets(Identity identity, Space space) {
		String url = ASSEMBLA_API_URL + "spaces/" + space.getId() + "/tickets" + TYPE_JSON;
		TicketList ticketList = get(url, identity, TicketList.class);
		return ticketList;
	}

	public List<Ticket> getSpaceTickets(Identity identity, Space space, Ticket.State state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("report", (state == State.OPEN) ? "1" : "4");
		map.put("page", 0);
		map.put("per_page", 100); // default
		String url = ASSEMBLA_API_URL + "spaces/" + space.getId() + "/tickets" + TYPE_JSON;
		TicketList ticketList = get(url, identity, TicketList.class, map);
		return ticketList;
	}

	// FIXME find better method to GET list converted
	private static final class SpaceList extends ArrayList<Space> {
		private static final long serialVersionUID = 1L;
	}

	private static final class SpaceToolList extends ArrayList<SpaceTool> {
		private static final long serialVersionUID = 1L;
	}

	private static final class TicketList extends ArrayList<Ticket> {
		private static final long serialVersionUID = 1L;
	}

}
