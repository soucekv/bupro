package cz.cvut.fel.reposapi.assembla.client;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.http.HttpHeaders;

/**
 * Token is session for client application
 * @author Viktor Soucek
 *
 */
@JsonIgnoreProperties
public class Token implements Identity {

	@JsonProperty("access_token")
	private String access_token;
	@JsonProperty("token_type")
	private String token_type;
	@JsonProperty("expires_in")
	private String expires_in;

	@JsonIgnore
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	@JsonIgnore
	private Date date;

	public String getAccessToken() {
		return access_token;
	}

	public String getTokenType() {
		return token_type;
	}

	public String getExpiresIn() {
		return expires_in;
	}

	public void setExpiresIn(String expiresIn) {
		this.expires_in = expiresIn;
	}

	protected void initExpiration() {
		date = new Date(System.currentTimeMillis() + timeUnit.toMillis(Long.valueOf(expires_in)));
	}

	public boolean isExpired() {
		return new Date().after(date);
	}

	public void authorize(HttpHeaders headers) {
		headers.set("authorization", getTokenType() + " " + getAccessToken());
	}

	@Override
	public String toString() {
		return getClass().getName() + " [access_token=" + access_token + ", token_type=" + token_type + ", expires_in=" + expires_in + "]";
	}

}
