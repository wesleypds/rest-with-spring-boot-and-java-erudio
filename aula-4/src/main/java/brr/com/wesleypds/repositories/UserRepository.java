package brr.com.wesleypds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import brr.com.wesleypds.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User WHERE u.userName =:userName")
    User findByUserName(@Param("userName") String userName);

}
