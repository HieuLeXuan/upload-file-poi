package com.hieulexuan.uploadexcelfile.user.repository;

import com.hieulexuan.uploadexcelfile.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
