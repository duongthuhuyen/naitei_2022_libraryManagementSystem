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
    
	@Column
    private String name;
    

	@Column
    private String email;
    @Column
    private String password;
	@Transient
	private String matchingPassword;

    
	@Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50)
    private ERole role;

    
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
