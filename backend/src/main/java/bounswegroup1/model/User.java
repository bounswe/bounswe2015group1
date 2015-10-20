package bounswegroup1.model;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Base64;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class User {
	private Long id;
	

	@NotNull
    private String email;
	
	@NotNull
    private String passwordHash;
	
	@NotNull
    private String passwordSalt;
	
    private String fullName;
    private String location;
    private Date dateOfBirth;
    

    public User(){

    }

	public User(Long id, String email, String passwordHash, String passwordSalt, String fullName,
			String location, Date dateOfBirth) {
		super();
		this.id = id;
		this.email = email;
		this.passwordHash = passwordHash;
		this.passwordSalt = passwordSalt;
		this.fullName = fullName;
		this.location = location;
		this.dateOfBirth = dateOfBirth;
	}
	
	@JsonSetter("password")
	public void setPassword(String password){
		System.out.println("set password");
		
		final Random r = new SecureRandom();
		byte[] salt = new byte[128];
		r.nextBytes(salt);
		
		String saltStr = Base64.encodeBase64String(salt);
		
		this.passwordSalt = saltStr;
		this.passwordHash = DigestUtils.sha256Hex(saltStr + password);
	}
	
	public boolean checkPassword(String password){
		String hashed = DigestUtils.sha256Hex(this.passwordSalt + password);
		
		return hashed == this.passwordHash;
	}

	@JsonGetter("email")
	public String getEmail() {
		return email;
	}

	@JsonGetter("fullName")
	public String getFullName() {
		return fullName;
	}

	@JsonGetter("location")
	public String getLocation() {
		return location;
	}

	@JsonGetter("dateOfBirth")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	@JsonSetter("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonSetter("fullName")
	public void setFullName(String fullName) {
		this.fullName = fullName;
		
	}

	@JsonSetter("location")
	public void setLocation(String location) {
		this.location = location;
	}

	@JsonSetter("dateOfBirth")
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@JsonIgnore
	public String getPasswordHash() {
		return passwordHash;
	}

	@JsonIgnore
	public String getPasswordSalt() {
		return passwordSalt;
	}
	
	@JsonGetter("id")
	public Long getId() {
		return id;
	}

	@JsonSetter("id")
	public void setId(Long id) {
		this.id = id;
	}
}
