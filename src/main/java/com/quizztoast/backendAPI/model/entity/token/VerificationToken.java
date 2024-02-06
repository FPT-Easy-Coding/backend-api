package com.quizztoast.backendAPI.model.entity.token;

import com.quizztoast.backendAPI.model.entity.user.User;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION_TIME = 15;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String token;

    private Date expirationTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken( User user,String token) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime =this.getTokenExpirationTime();
    }
    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime =this.getTokenExpirationTime();
    }

    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
