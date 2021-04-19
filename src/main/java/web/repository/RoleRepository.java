package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository <Role, Long> {

    Role findByRole(String roleName);
}
