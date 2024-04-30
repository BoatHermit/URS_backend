package com.nju.urs.user.repository;
import com.nju.urs.user.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
// import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserPO, String> {
    Optional<UserPO> findByNameAndPassword(String name,String password);

    Optional<UserPO> findByPhoneAndPassword(String phone, String password);
    Optional<UserPO> findByPhone(String phone);
    Optional<UserPO> findByName(String name);
}
