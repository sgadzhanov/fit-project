package com.softuni.fitshop.repository;

import com.softuni.fitshop.model.entity.UserRoleEntity;
import com.softuni.fitshop.model.entity.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    UserRoleEntity findByRoleEnum(UserRoleEnum roleEnum);

}
