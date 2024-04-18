package call.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import call.log.entity.Child;

public interface ChildDao extends JpaRepository<Child, Long> {


}
