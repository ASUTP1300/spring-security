package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Role;
import web.repository.RoleRepository;
import web.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> getRoleByName(List<String> listStingRoles){
        return   listStingRoles.stream()
                .map(s -> roleRepository.findByRole(s))
                .collect(Collectors.toSet());
    }
}
