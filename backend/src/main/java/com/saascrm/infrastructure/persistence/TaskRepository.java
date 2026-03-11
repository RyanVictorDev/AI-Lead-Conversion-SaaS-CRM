package com.saascrm.infrastructure.persistence;

import com.saascrm.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

