package ru.gb.zaripov.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.zaripov.auth.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
