package com.albert.resumeAi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * User persistence layer.
 *
 * This is just an interface. Spring Data JPA generates the implementation
 * at runtime, so you write zero method bodies.
 *
 * Requirements:
 *  - Extend JpaRepository<User, UUID>.
 *    That gives you for free:
 *        save(User), findById(UUID), findAll(), deleteById(UUID),
 *        count(), existsById(UUID), and more.
 *
 *  - Declare custom finder methods as you need them. Spring derives the SQL
 *    from the method name — no implementation required. Examples likely
 *    useful for Phase 1:
 *        Optional<User> findByEmail(String email);
 *        boolean existsByEmail(String email);
 *
 *  - No @Repository annotation needed. Extending JpaRepository is enough.
 *
 * Things NOT to do:
 *  - Don't write @Query strings unless a derived method can't express the
 *    query. The naming convention covers ~90% of cases.
 *  - No business logic here. Repositories ONLY talk to the database.
 *  - Don't return entities you mutated. Repository methods are pass-through.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    // TODO: declare custom finder methods you need (e.g. findByEmail, existsByEmail)

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
