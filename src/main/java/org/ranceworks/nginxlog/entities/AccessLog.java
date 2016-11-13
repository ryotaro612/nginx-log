package org.ranceworks.nginxlog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AccessLog {

	  @Id
	  @GeneratedValue
	  Long id;

	  // http://www.developerscrappad.com/228/java/java-ee/ejb3-jpa-dealing-with-date-time-and-timestamp/
	  /*
	  @Column(name = "DATE_FIELD")
	  private java.sql.Date dateField;
	      
	  @Column(name = "TIME_FIELD")
	  private java.sql.Time timeField;
	      
	  @Column(name = "DATETIME_FIELD")
	  private java.sql.Timestamp dateTimeField;
	      
	  @Column(name = "TIMESTAMP_FIELD")
	  private java.sql.Timestamp timestampField; 
	  */
}
