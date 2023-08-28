package com.pro.infomate.member.repository;

import com.pro.infomate.member.entity.AuthList;
import com.pro.infomate.member.entity.AuthListPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthListRepository extends JpaRepository<AuthList, AuthListPK> {

}
