package com.example.BackEnd.Member.Entity;

import com.example.BackEnd.common.entity.BaseTimeEntity;
import lombok.*;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@SQLDelete(sql = "update members set deleted = true where member_id = ?")
@SQLRestriction("deleted = false")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // 사용자가 입력하는 ID
    @Column(unique = true, nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false)
    private String profileImageUrl;

    @Column(nullable = false)
    private boolean deleted;

    private LocalDateTime deletedAt;

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void changeProfileImage(String newUrl) {
        this.profileImageUrl = newUrl;
    }

    public void deleteMember() {
        this.deletedAt = LocalDateTime.now();
    }

}
