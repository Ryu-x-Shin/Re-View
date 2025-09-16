package com.example.BackEnd.Member.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "members")
public class Member {

    // PK -> Auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자가 입력하는 ID
    @Column(unique = true, nullable = false, length = 12)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false, length = 12)
    private String nickname;

    @Column(nullable = false)
    private String profileImageUrl;

    @Column(nullable = false)
    private boolean deleted = false;

    private String refreshTokenHash;

    // 변경 메서드
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void changeProfileImage(String newUrl) {
        this.profileImageUrl = newUrl;
    }

    public void changeDeleted(boolean newStatus) {
        this.deleted = newStatus;
    }

    public void changeRefreshTokenHash(String newHash) {
        this.refreshTokenHash = newHash;
    }

}
