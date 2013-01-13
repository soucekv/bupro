package cz.cvut.fel.bupro;

import java.sql.Timestamp;
import java.util.Date;

public class TimeUtils {

	public static Timestamp createCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}
}
