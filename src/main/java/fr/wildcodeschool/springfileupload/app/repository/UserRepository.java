package fr.wildcodeschool.springfileupload.app.repository;

import fr.wildcodeschool.springfileupload.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
