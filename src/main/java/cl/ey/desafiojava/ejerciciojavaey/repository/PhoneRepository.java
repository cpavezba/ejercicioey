package cl.ey.desafiojava.ejerciciojavaey.repository;

import cl.ey.desafiojava.ejerciciojavaey.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

    Set<Phone> findByUserId(UUID id);
}
