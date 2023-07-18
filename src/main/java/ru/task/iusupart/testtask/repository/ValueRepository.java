package ru.task.iusupart.testtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.iusupart.testtask.model.Value;

@Repository
public interface ValueRepository extends JpaRepository<Value, Integer> {
}

