package com.example.bookrental.repo;

import com.example.bookrental.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepo extends JpaRepository<Member ,Long> {

    Optional<Member> findByName(String name);
}
