package com.example.bookrental.Repo;

import com.example.bookrental.Entity.Catagory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatagoryRepo extends JpaRepository<Catagory, Long> {
}
