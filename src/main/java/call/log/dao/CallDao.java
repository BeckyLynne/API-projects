package call.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import call.log.entity.PhoneCall;

public interface CallDao extends JpaRepository<PhoneCall, Long> {


}
