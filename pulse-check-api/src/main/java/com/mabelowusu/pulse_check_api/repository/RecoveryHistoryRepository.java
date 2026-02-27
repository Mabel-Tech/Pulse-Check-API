package com.mabelowusu.pulse_check_api.repository;

import com.mabelowusu.pulse_check_api.model.RecoveryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryHistoryRepository extends JpaRepository<RecoveryHistory, Long> {

}
