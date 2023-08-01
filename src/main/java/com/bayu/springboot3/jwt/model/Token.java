package com.bayu.springboot3.jwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token")
    private String strToken;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "revoked")
    private Boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_token_user_id"), referencedColumnName = "id")
    private User user;

}
