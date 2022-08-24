package com.sun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column
    private String name;
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
    private String email;
    @Column
    private String password;
	@Transient
	private String matchingPassword;

    public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	@Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50)
    private ERole role;

    public ERole getRole() {
		return role;
	}

	public void setRole(ERole role) {
		this.role = role;
	}

	public enum ERole{
        ADMIN,USER;
        public static Optional<ERole> check(String val) {
            try {
                return Optional.of(ERole.valueOf(val));
            }
            catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
    }

}
