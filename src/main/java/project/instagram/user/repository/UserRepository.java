package project.instagram.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.instagram.domain.User;

public interface UserRepository extends JpaRepository<User,Long>,UserRepositoryCustom {
}
