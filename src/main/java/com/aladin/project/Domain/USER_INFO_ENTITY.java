package com.aladin.project.Domain;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_USERS_INFO")
public class USER_INFO_ENTITY {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "birth")
    private String birth;

    @Column(name = "sex_cd")
    private String sexCd;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "del_yn", nullable = false)
    private String delYn;

    @Column(name = "frs_rgstrn_dtm", nullable = false)
    private LocalDateTime frsRgstrnDtm;

    @Column(name = "frs_rgstrn_id", nullable = false)
    private String frsRgstrnId;

    @Column(name = "last_chng_dtm", nullable = false)
    private LocalDateTime lastChngDtm;

    @Column(name = "last_chng_id", nullable = false)
    private String lastChngId;

    @Column(name = "guid", nullable = false)
    private String guid;
}
